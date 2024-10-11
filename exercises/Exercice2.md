
# Using PMD


Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset (see the [pmd install instruction](./pmd-help.md)). Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.


## Answer

After picking the commons-math project from Github and after running PMD on its source code, we found the following error that we think should be solved (true positive):

C:\Users\franc\OneDrive\Documents\ESIR\ESIR2\VV\commons-math\commons-math-core\src\main\java\org\apache\commons\math4\core\jdkmath\AccurateMath.java:4070:	CloseResource:	Ensure that resources like this PrintStream object are closed after use

We would modify this part oh the code :
    "PrintStream out = System.out;"
by this :
    "try (PrintStream out = System.out;) {
    
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }"

With this modification that would ensures that the PrintStream is closed automatically, preventing resource leaks.



Here is an issue that would not be worth solving :

    C:\Users\franc\OneDrive\Documents\ESIR\ESIR2\VV\commons-math\commons-math-core\src\main\java\org\apache\commons\math4\core\jdkmath\AccurateMath.java:396:	UselessParentheses:	Useless parentheses.

We wouldn't solve this issue because even if it might not be necessary, it's not really significant in a bad way and it improves the readability of the expression.