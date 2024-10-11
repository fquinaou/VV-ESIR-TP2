package fr.istic.vv;

import com.github.javaparser.Problem;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException {

        String chemin = "";
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Write the source code path: ");
            chemin = scanner.nextLine();
        }

        if(chemin.length() == 0) {
            System.err.println("Should provide the path to the source code");
            System.exit(1);
        }

        File file = new File(chemin);
        if(!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a path to an existing readable directory");
            System.exit(2);
        }

        SourceRoot root = new SourceRoot(file.toPath());
        //PublicElementsPrinter printer = new PublicElementsPrinter();
        NoGetter printer = new NoGetter();

        root.parse("", (localPath, absolutePath, result) -> {
            result.ifSuccessful(unit -> unit.accept(printer, null));
            return SourceRoot.Callback.Result.DONT_SAVE;
        });
        printer.toCsv();
        printer.print();
        
    }


}
