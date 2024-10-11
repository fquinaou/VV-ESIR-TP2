package fr.istic.vv;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;


// This class visits a compilation unit and
// prints all public enum, classes or interfaces along with their public methods
public class NoGetter extends VoidVisitorWithDefaults<Void> {


    private HashMap<String,String> methods;
    private HashMap<String,String> fields;
    private ArrayList<String> del;
    private static final String DELIMITER = ",";
    private static final String SEPARATOR = "\n";
    private static final String HEADER = "Package and Class,Field";
    private String packa = "";
    
   

    
    public NoGetter() {
        this.methods = new HashMap<>();
        this.fields = new HashMap<>();
        this.del = new ArrayList<>();
    }

    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for(TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);
        }
    }

    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        
        packa = declaration.getFullyQualifiedName().orElse("[Anonymous]");

       

        for(FieldDeclaration field : declaration.getFields()) {
            field.accept(this, arg);
        }
        
        for(MethodDeclaration method : declaration.getMethods()) {
            method.accept(this, arg);
        }
        
        // Printing nested types in the top level
        for(BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration)
                member.accept(this, arg);
        }
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);
    }

    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        String meth = declaration.getDeclarationAsString(true, true);
        
        
        if(meth.contains("get")){
            this.methods.put(meth,this.packa);
        }
       
    }

    @Override
    public void visit(FieldDeclaration declaration, Void arg) {
        
        declaration.getVariables().forEach(variable -> { 
            
            String fieldName = variable.getNameAsString();
            
            this.fields.put(fieldName,this.packa);
            
        });
        
        
    }

    public void print(){
        
        System.out.println(fields.keySet());
        System.out.println(methods.keySet());
        
    }

    public void toCsv(){
        for (Map.Entry<String, String> set :
             fields.entrySet()) {

                for (Map.Entry<String, String> metho :
             methods.entrySet()) {
                if(set.getValue().equals(metho.getValue())){
                    if(metho.getKey().toLowerCase().contains(set.getKey())){
                        del.add(set.getKey());
                    }
                }
             }
  
        }

        del.forEach(gett->{
            this.fields.remove(gett);
        });
        FileWriter file = null;
        

      
      try
      {
        file = new FileWriter("Attributs.csv");
        file.append(HEADER);
        file.append(SEPARATOR);

        for (Map.Entry<String, String> set :
             fields.entrySet()) {

            file.append(set.getValue());
            file.append(DELIMITER);
            file.append(set.getKey());
            file.append(SEPARATOR);
        }
              
        file.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    }

}
