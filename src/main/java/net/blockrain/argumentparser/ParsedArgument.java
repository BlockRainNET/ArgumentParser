package net.blockrain.argumentparser;

public class ParsedArgument
{
    private final Argument from;
    private final String content;

    public ParsedArgument(Argument from, String content)
    {
        this.from = from;
        this.content = content;
    }

    public Argument getFrom()
    {
        return from;
    }

    public String getContent()
    {
        return content;
    }
}