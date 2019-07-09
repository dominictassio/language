package party.tassio.language.visitor;

import party.tassio.language.antlr.LanguageBaseVisitor;
import party.tassio.language.antlr.LanguageParser;

public class LanguageVisitor extends LanguageBaseVisitor<Void> {
    private StringBuilder builder = new StringBuilder();

    public Void visitTop_level(LanguageParser.Top_levelContext ctx) {
        builder.append('(');
        visitChildren(ctx);
        builder.append(')');
        return null;
    }

    public Void visitLclass(LanguageParser.LclassContext ctx) {
        builder.append("(class ").append(ctx.IDENTIFIER().getText()).append(" (");

        if (ctx.expression() != null) {
            visitChildren(ctx.expression());
        }

        builder.append(") ");

        if (ctx.class_fields() != null) {
            visitChildren(ctx.class_fields());
        }

        builder.append(") ");
        return null;
    }

    public Void visitClass_field(LanguageParser.Class_fieldContext ctx) {
        if (ctx.IDENTIFIER() != null) {
            builder.append('(').append(ctx.IDENTIFIER().getText()).append(' ');
            visitChildren(ctx.class_statement());
            builder.append(") ");
        } else {
            visitChildren(ctx.class_statement());
        }

        return null;
    }

    public Void visitLenum(LanguageParser.LenumContext ctx) {
        builder.append("(enum ").append(ctx.IDENTIFIER().getText()).append(' ');

        if (ctx.enum_fields() != null) {
            visitChildren(ctx.enum_fields());
        }

        builder.append(") ");
        return null;
    }

    public Void visitEnum_field(LanguageParser.Enum_fieldContext ctx) {
        builder.append('(').append(ctx.IDENTIFIER().getText()).append(' ');

        if (ctx.expression() != null) {
            visitChildren(ctx.expression());
        }

        builder.append(") ");
        return null;
    }

    public Void visitFunction(LanguageParser.FunctionContext ctx) {
        builder.append("(fn ").append(ctx.IDENTIFIER().getText()).append(" ");

        if (ctx.function_left() != null) {
            visitChildren(ctx.function_left());
        } else {
            builder.append("() ");
        }

        if (ctx.function_right() != null) {
            visitChildren(ctx.function_right());
        } else {
            builder.append("() ");
        }

        if (ctx.type_specifier() != null) {
            visitChildren(ctx.type_specifier());
        } else {
            builder.append("() ");
        }

        if (ctx.statements() != null) {
            visitChildren(ctx.statements());
        }

        builder.append(") ");
        return null;
    }

    public Void visitRules(LanguageParser.RulesContext ctx) {
        builder.append("(rules ").append(ctx.IDENTIFIER().getText()).append(' ');

        if (ctx.rules_fields() != null) {
            visitChildren(ctx.rules_fields());
        }

        builder.append(") ");
        return null;
    }

    public Void visitRules_field(LanguageParser.Rules_fieldContext ctx) {
        builder.append('(');
        visitChildren(ctx);
        builder.append(") ");
        return null;
    }

    public Void visitAssignment_let(LanguageParser.Assignment_letContext ctx) {
        builder.append("(let ");
        visitChildren(ctx.assignment_left());

        if (ctx.type_specifier() != null) {
            visitChildren(ctx.type_specifier());
        } else {
            builder.append("() ");
        }

        visitChildren(ctx.assignment_right());
        builder.append(") ");
        return null;
    }

    public Void visitAssignment_mut(LanguageParser.Assignment_mutContext ctx) {
        builder.append("(mut ");
        visitChildren(ctx.assignment_left());

        if (ctx.type_specifier() != null) {
            visitChildren(ctx.type_specifier());
        } else {
            builder.append("() ");
        }

        if (ctx.assignment_right() != null) {
            visit(ctx.getChild(ctx.getChildCount() - 2));
        } else {
            builder.append("() ");
        }
        builder.append(") ");
        return null;
    }

    public Void visitMatch_statement(LanguageParser.Match_statementContext ctx) {
        builder.append("(match ");
        visitChildren(ctx.match_matchee());

        if (ctx.match_fields() != null) {
            visitChildren(ctx.match_fields());
        }

        builder.append(") ");
        return null;
    }

    public Void visitMatch_field(LanguageParser.Match_fieldContext ctx) {
        builder.append('(');
        visitChildren(ctx);
        builder.append(") ");
        return null;
    }

    public Void visitWhilematch_statement(LanguageParser.Whilematch_statementContext ctx) {
        builder.append("(whilematch ");
        visitChildren(ctx.whilematch_matchee());

        if (ctx.whilematch_fields() != null) {
            visitChildren(ctx.whilematch_fields());
        }

        if (ctx.statements() != null) {
            visitChildren(ctx.statements());
        }

        builder.append(")" );
        return null;
    }

    public Void visitWhilematch_field(LanguageParser.Whilematch_fieldContext ctx) {
        builder.append('(');
        visitChildren(ctx);
        builder.append(") ");
        return null;
    }

    public Void visitExpression(LanguageParser.ExpressionContext ctx) {
        for (LanguageParser.LiteralsContext literalsCtx : ctx.literals()) {
            builder.append("(expr ");
            visitChildren(literalsCtx);
            builder.append(") ");
        }

        return null;
    }

    public Void visitLiterals(LanguageParser.LiteralsContext ctx) {
        if (ctx.literal().size() == 1) {
            visitChildren(ctx);
        } else {
            builder.append('(');
            visitChildren(ctx);
            builder.append(") ");
        }

        return null;
    }

    public Void visitLiteral(LanguageParser.LiteralContext ctx) {
        builder.append(ctx.getText());
        builder.append(' ');
        return null;
    }

    @Override
    public String toString() {
        return builder.toString().replace(" )", ")");
    }
}
