# TCC *vs* LCC

Explain under which circumstances *Tight Class Cohesion* (TCC) and *Loose Class Cohesion* (LCC) metrics produce the same value for a given Java class. Build an example of such as class and include the code below or find one example in an open-source project from Github and include the link to the class below. Could LCC be lower than TCC for any given class? Explain.

A refresher on TCC and LCC is available in the [course notes](https://oscarlvp.github.io/vandv-classes/#cohesion-graph).

## Answer

TCC and LCC metrics can produce the same value for a given Java class, if all methods that are connected are directly connected(methods share the same instance variable), no indirectly connections, for example if all nodes are disconnected on the graph because the methods don't have instance variables in common the TCC and LCC metrics both give a value of 0.

Here an example of a class where TCC and LCC have the same value (0 and 0) :

class Group {

    private int weight;
    private String name;
    private Color color;

    public Group(String name, Color color, int weight) {
        this.name = name;
        this.color = color;
        this.weight = weight;
    }

    public int compareTo(Group other) {
        return weight - other.weight;
    }

    public void draw() {
        Screen.rectangle(color, name);
    }

}

Here an example of a class where TCC = LCC but their values aren't 0 :

public class ExampleClass {
    private int x;
    private int y;

    
    public int sum() {
        return x + y;
    }

    public int multiply() {
        return x * y;
    }

    public void setValues(int value1, int value2) {
        x = value1;
        y = value2;
    }
    public void reset() {
        x = 0;
        y = 0;
    }
}

LCC can't be lower than TCC because TCC only considers direct connections between methods where LCC considers direct and indirect connections, so by definition every connection considered by TCC is also considered by LCC and LCC can also count indiect connections.