# ArbitraryGen

本文章介绍的是我写的一个支持混合式代码生成器ArbitraryGen，该项目经历了好多个版本，设计了n编了，走了无数的弯路，刚开始搞这个项目的时候应该是2014年我刚毕业没多久，我已经记得不太清了，现在它基本稳定了，还算是超级好用，具有超强的可扩展性。
## 改代码生成器包括三种模式：
#### （1）普通代码生成模式，直接通过在生成代码的代码中指定生成代码的样式和和模板；（思路简单，效率高，但扩展性和灵活性没那么高）
#### （2）脚本引擎模式，模板是通过脚本定义，使用脚本引擎来生成代码，相比（1）具有较高的灵活性和扩展性；
#### （3）混合式代码生成模式，模板支持脚本和源码混合的方式定义，想比（2）更为灵活和可扩展性；
其实还有另外一种模式，不过那个相当复杂，并且还没完善，算是一个败笔，在此不说！

下面我就讲第三种模式：混合式代码生成器的使用和一些设计点吧。

## 一、混合式代码生成器具有的特性：

### 1、将脚本和源码混合到代码的源文件中；
### 2、代码生成操作是可持续的；
### 3、脚本在代码生成后不会被删除，而是将生成的代码附于脚本区域之后，以便于脚本的可持续使用；
### 4、每次生成代码前会将上次生成的代码删除；


## 二、脚本区

之所以要将脚本代码归类到某个脚本代码块中，是因为要保持脚本的可持续生成代码的能力，也就是在代码生成完成后，重新clean build能让代码重新生成。

代码区由/*@@@#SCRIPT-BEGIN# 和 #SCRIPT-END#@@@*/ 包裹着，在其中的便是脚本代码，其实生成的代码将被放在//@@@#AUTO-GEN-BEGIN# 和 //@@@#AUTO-GEN-END# 之间。

为了区分脚本代码和源文件中的源代码，我们将脚本代码放在了<% %> 标签之中。脚本代码可以有两种形式，一种是普通的代码逻辑，另一种是直接赋值操作；直接赋值操作的格式为：<%=scriptCode %>。

## 三、脚本示例

示例：
    package cc.suitalk.player.autogen.table;

    import android.util.Log;

    import cc.suitalk.sdk.db.base.IDatabaseEngine;
    import cc.suitalk.sdk.db.base.IDatabaseInfoDelegate;

    /*@@@#SCRIPT-BEGIN#
    <%if (_dbItems && _dbItems.length > 0) {%>
    	<%for (var i = 0; i < _dbItems.length; i++) {%>
    import <%=_package%>.<%=_dbItems[i]%>;
    	<%}%>
    <%}%>
    #SCRIPT-END#@@@*/

    /**
     * Generated by ScriptCodeGenEngine.
     * <p>
     * Auto generate add {@link IVigorDBInfo} into {@link IDatabaseEngine} here.
     *
     * @author AlbieLiang
     *
     */
    public class VDBInfoDelegate implements IDatabaseInfoDelegate {

    	private static final String TAG = "AG.VDBInfoDelegate";

    	@Override
    	public void delegate(IDatabaseEngine engine) {
    		// Auto generate code here

    		/*@@@#SCRIPT-BEGIN#
    		<%if (_dbItems && _dbItems.length > 0) {%>
    			<%for (var i = 0; i < _dbItems.length; i++) {%>
    				engine.addDatabaseInfo(<%=_dbItems[i]%>.getVDBInfo());
    			<%}%>
    		<%}%>
    		#SCRIPT-END#@@@*/

    		//@@@#AUTO-GEN-BEGIN#
    		// @date 2016-02-04 17:34:11
    		Log.d(TAG, "Auto-Gen Code log.");
    		//@@@#AUTO-GEN-END#
    	}

    }


## 四、使用方法

使用方法其实很简单，其包括（1）数据源的定义；（2）模板文件；（3）工程中如何引入使用；

### 1、数据源：
数据源是以.hybrids-define为后缀的文件，文件格式是XML，其中二级节点作为数据源的元数据；
（数据源文件的位置将在后面的“3、在项目中引入方法”说明）

示例如下：
    <?xml version="1.0" encoding="UTF-8"?>
    <hybrids-define
    	package="cc.suitalk.player.autogen.table"
    	delegateDest="src/main/java/cc/suitalk/player/autogen/table"
    	delegatePkg="cc.suitalk.player.autogen.table"
    	delegate="VDBInfoDelegate"
    	delegateSuffix="java"
    	tag="table">

    	<!--
    	    * 根节点属性：
    	    *
    	    * package        : 子节点用于生成代码的包名
    	    * delegate       : 委派模板文件名（收敛子节点的统一文件）
    	    * delegateDest   : 委派模板文件所在的根目录（注：该模式下模板和生成的文件是同一个）
    	    * delegatePkg    : 委派模板文件的包名
    	    * delegateSuffix : 委派模板文件的文件后缀（为了支持不同语言）
    	    * tag            : 作为委派模板数据源的子节点集合，“,”作为分隔符，tag与模板文件的映射关系可在template-libs/template-mapping.properties中指定
    	    *
    	-->

    	<!--
    		* acceptable type for fields:
    		*
    		* boolean <==> INT(0/1) <br />
    		* int/Integer <==> INT <br/>
    		* long/Long <==> LONG <br/>
    		* short/Short <==> SHORT <br/>
    		* float/Float <==> FLOAT <br/>
    		* double/Double <==> DOUBLE <br/>
    		* String <==> TEXT <br/>
    		* byte[] <==> BLOB <br/>
    		* protobuf / referto <==> BLOB<br/>
    	-->


    	<!-- The rowId of this table will be use as primaryKey. -->
    	<table name="DBItem_1">
    		<field name="field_1" type="String" comment="for duplicate removal"/>
    		<field name="field_2" type="String"/>
    		<field name="field_3" type="byte[]">
    			<part name="field_part_1" type="long"/>
     		</field>
    		<field name="field_4" type="boolean" default="false" index="Table_1_index”/>

    		<index name="field_3_and_1_index" fields="field_3,field_1"/>
    	</table>

    	<!-- The vigor entry will not be created for this table because noEntry is true. -->
    	<table name="DBItem_2" noEntry="true">
    		<field name="field_1" type="String" primaryKey="1"/>
    		<field name="field_2_2" type="String"/>
    		<field name="field_2_3" type="String"/>
    		<field name="field_2_4" type="byte[]">
    			<part name="field_part_1" type="long"/>
     		</field>
    	</table>

    	<table name="DBItem_3">
    		<field name="field_3_1" type="String" primaryKey="1"/>
    		<field name="field_3_2" type="String"/>
    		<field name="field_3_3" type="byte[]">
    			<part name="field_part_1" type="long"/>
     		</field>
    	</table>

    	<table name="DBItem_4">
    		<field name="field_4_1" type="String" primaryKey="1"/>
    		<field name="field_4_2" type="String"/>
    	</table>

    	<!-- Multiply primary key sample -->
    	<table name="DBItem_5">
    		<field name="field_5_1" type="String" primaryKey="1"/>
    		<field name="field_5_2" type="int" primaryKey="2"/>
    		<field name="field_5_3" type="long"/>
    	</table>
    </hybrids-define>


### 2、代码模板

模板程序就是普通的java文件，脚本被嵌入到源码中的/*@@@#SCRIPT-BEGIN# 和 #SCRIPT-END#@@@*/ 之中，则每个脚本区生成的代码将append到脚本区域后面，处于//@@@#AUTO-GEN-BEGIN# 和 //@@@#AUTO-GEN-END# 注释之间。示例如下：
    package cc.suitalk.player.autogen.table;

    import android.util.Log;

    import cc.suitalk.sdk.db.base.IDatabaseEngine;
    import cc.suitalk.sdk.db.base.IDatabaseInfoDelegate;

    /*@@@#SCRIPT-BEGIN#
    <%if (_tables && _tables.length > 0) {%>
    	<%for (var i = 0; i < _tables.length; i++) {%>
    import <%=_package%>.<%=_tables[i]._name%>;<%}%>
    <%}%>
    #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#




    import cc.suitalk.player.autogen.table.DBItem_1;
    import cc.suitalk.player.autogen.table.DBItem_2;
    import cc.suitalk.player.autogen.table.DBItem_3;
    import cc.suitalk.player.autogen.table.DBItem_4;
    import cc.suitalk.player.autogen.table.DBItem_5;


    //@@@#AUTO-GEN-END#
    /**
     * Generated by ScriptCodeGenEngine.
     * <p>
     * Auto generate add {@link IVigorDBInfo} into {@link IDatabaseEngine} here.
     *
     * @author AlbieLiang
     *
     */
    public class VDBInfoDelegate implements IDatabaseInfoDelegate {

       private static final String TAG = "AG.VDBInfoDelegate";

       @Override
       public void delegate(IDatabaseEngine engine) {
          // Auto generate code here

          /*@@@#SCRIPT-BEGIN#
           <%if (_tables && _tables.length > 0) {%>
                <%for (var i = 0; i < _tables.length; i++) {%>
           engine.addDatabaseInfo(<%=_tables[i]._name%>.getVDBInfo());<%}%>
           <%}%>
           #SCRIPT-END#@@@*///@@@#AUTO-GEN-BEGIN#



          engine.addDatabaseInfo(DBItem_1.getVDBInfo());
          engine.addDatabaseInfo(DBItem_2.getVDBInfo());
          engine.addDatabaseInfo(DBItem_3.getVDBInfo());
          engine.addDatabaseInfo(DBItem_4.getVDBInfo());
          engine.addDatabaseInfo(DBItem_5.getVDBInfo());


    //@@@#AUTO-GEN-END#
       }
    }

#### 1）模板中使用到的主要数据数据来自于数据源，主要变量如下：

##### （1）变量_package，来源于根节点的package属性，改值将传递到子节点的处理程序当中；
##### （2）变量_name，来源于根节点的delegate属性；
##### （3）列表数据，来源于二级标签，其中脚本中变量名规则是：数据源中的二级标签名字前面加个下滑下，并在末尾加上一个”s"（如：示例中的_tables）；会有一种特殊的情况出现，就是标签的名字中包含“-”，这时候，会自动将“-”自动使用下划线“_”代替。

#### 2）元数据的处理：
看到这里你可能会觉得有哪些地方不太对的样子是吧，恩，没错，这些元数据都干什么用呢？其实在一开始的数据源文件中，已经有一段注释提示到了，就是根节点中的tag属性的作用，其映射了一个代码生成模板文件，用来处理这些元数据的，哈哈，映射关系需要写在template-libs/template-mapping.properties文件当中。

// todo 加上模板和生成的代码。

### 3、在项目中引入方法

#### 1）在Android Studio中引入：
其实严格来说就是支持gradle 构建的工程中引入的方法如下：

##### （1）gradle插件方式：

在project下的build.gradle文件中引入插件：

    buildscript {
        repositories {
            jcenter()
            maven {
                url 'file://Volumes/Development/git-repository/tools-repository/ArbitraryGen/arbitrarygen-plugin/plugin-output/release'
            }
        }

        dependencies {
            classpath 'com.android.tools.build:gradle:2.1.2'
            classpath 'osc.innovator.gradle.plugin:arbitrarygen:1.0.0'
        }
    }
    allprojects {
        repositories {
            jcenter()
            maven {
                url 'file://Volumes/Development/git-repository/tools-repository/ArbitraryGen/arbitrarygen-plugin/plugin-output/release'
            }
        }
    }
在module下配置ArbitraryGen参数：

    apply plugin: 'com.android.library'
    apply plugin: 'arbitrarygen' //引入插件
    android {
        ...
    }
    dependencies {
        ...
    }
    // 参数配置
    arbitraryGen {
        libsDir "${project.rootDir.getAbsolutePath()}/ArbitraryGen" // 指定代码生成器资源库根目录，其中包含生成代码的模板，模板映射文件和代码生成器可执行文件等
        inputDir "../$name/autogen" // 指定数据源文件目录
        outputDir "$buildDir/generated/source/autogen" // 指定“普通”（相对于“混合式”）代码生成的目标目录
        // 普通设置相关的参数，下面只是列出一少部分，更多参数将在后续不上说明
        general {
            format "xml,event,db" // 指定数据源文件的后缀列表（非“混合式”代码生成器模块）
        }
        // log相关的参数，下面只是列出一少部分，更多参数将在后续不上说明
        logger {
            debug true
            logToFile true
            printArgs true
            path "$buildDir/outputs/log/ag.log" // 指定log输出目录
        }
    }
注意上面的libsDir参数，该参数对应着代码生成器资源根目录，需要将代码生成器的可执行文件ArbitraryGen.jar、模板映射文件
###### （2）直接使用gradle脚本调用方式：
这个其实也很简单，就是写一个运行可执行文件的命令而已，在这里我就暂时不讲，有需要的同学可以找我。

##### 2）在Eclipse中引入：
这个我不太想写了，大家直接用AS吧，高大上！（如果真的有人需要的话，在找我吧，最初的版本就是支持Eclipse的了，只是现在应该大家都转AS了就不多说了~）


## 五、代码解析流程

### 1、解析的大致流程
生成代码的流程，先用一个transfer脚本将模板文件（包含生成代码的脚本程序片段），转换成可执行的、完整的脚本程序，最后通过脚本引擎运行得到的脚本程序从而生成所需的代码程序。

实际上，模板文件是一个脚本程序和原代码混合的体，需要用transfer脚本去将模板文件里面的脚本和源码的混合体转换成脚本引擎能够执行的脚本程序，一句话就是脚本引擎运行用脚本去解析脚本得到的脚本！

### 2、模板文件的处理
解析思路：将模板文件切割成n段，将脚本代码和原代码分离，最终组合成一段脚本代码，再将数据源注入，通过脚本引擎运行脚本，生成想要的代码。

#### 1）将源代码扫描一遍，遇到脚本起始标识/*@@@#SCRIPT-BEGIN#，此处作为一个分割点，开始拼接脚本代码，直到遇到 #SCRIPT-END#@@@*/ 脚本区结束标识，结束脚本代码的拼接；
#### 2）遇到//@@@#AUTO-GEN-BEGIN# 已生成代码代码起始标识，程序将忽略后面的所有字符，直到遇到 //@@@#AUTO-GEN-END# 已生成代码结束标识才停止；
#### 3）其它非1）和2）情况的串，将当做普通的字符串拼接起来；
#### 4）经过上述的过程，可以得到一个完整可执行的脚步程序了；

### 3、其它
这篇文章主要是讲ArbitraryGen代码生成器的使用方法，后续将会写上它的具体实现，不过估计要写好多篇文章才行！


## 六、待解决问题
### 1、混合模式下，生成的代码会出现较多的换行问题；（这个问题后续再搞，只是看起来没那么好看而已，不影响使用）
### 2、脚本标签<%script code%>支持换行；（目前不支持）
### 3、java源码解析器，对方法参数带有Annotation这种case没有处理；


## 七、结语：
大家可能会吐槽为什么举的例子都是这么复杂的代码，而且很长，哈哈，莫急，这个其实是我写的另一套数据库框架，有空再写写文章，开源下这套东西，敬请期待。

其实这个代码生成器是一个不是简单的一套生成代码的小工具，它提供了部分可扩展的接口，提供SDK，若想是想更加强大功能的定制化的代码生成可以使用SDK进行开发，后续将陆续写上这一块的文章。代码生成器的实现，也将在后续文章中说明。敬请期待！
