# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](./designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

<rule name="AvoidDeeplyNestedIfStatements"
          language="java"
          class="net.sourceforge.pmd.lang.rule.xpath.XPathRule"
          message="Avoid using three or more nested if statements."
          severity="3">
        
<description>
            Cette règle détecte si il y a 3 if ou plus imbriqués.
</description>

<properties>
            <property name="xpath">
                <value>
                    //IfStatement[descendant::IfStatement/descendant::IfStatement]
                </value>
            </property>
    </properties>
</rule>


By using our custom rule, the pmd check command have found 220 cases of triple or more nested if statements, for example :

C:\Users\franc\OneDrive\Documents\ESIR\ESIR2\VV\commons-math\commons-math-core\src\main\java\org\apache\commons\math4\core\jdkmath\AccurateMath.java:392:	AvoidDeeplyNestedIfStatements:	Avoid using three or more nested if statements.
C:\Users\franc\OneDrive\Documents\ESIR\ESIR2\VV\commons-math\commons-math-core\src\main\java\org\apache\commons\math4\core\jdkmath\AccurateMath.java:462:	AvoidDeeplyNestedIfStatements:	Avoid using three or more nested if statements.
