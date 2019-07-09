package party.tassio.language;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import party.tassio.language.antlr.LanguageLexer;
import party.tassio.language.antlr.LanguageParser;
import party.tassio.language.visitor.LanguageVisitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

public class Language {
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: language [file]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        CharStream stream = CharStreams.fromPath(Paths.get(path));
        run(stream);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        while (true) {
            System.out.print("> ");
            String line = reader.readLine();

            if (line.equals(".quit")) {
                break;
            }

            run(CharStreams.fromString(line));
        }
    }

    private static void run(CharStream stream) {
        LanguageLexer lexer = new LanguageLexer(stream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LanguageParser parser = new LanguageParser(tokens);
        ParseTree tree = parser.top_level();
        LanguageVisitor visitor = new LanguageVisitor();
        visitor.visit(tree);
        System.out.println(visitor.toString());
    }
}
