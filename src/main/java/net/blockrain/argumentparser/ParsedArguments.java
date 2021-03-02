package net.blockrain.argumentparser;

import java.util.Arrays;
import java.util.List;

public class ParsedArguments
{
    private final List<ParsedArgument> arguments;

    public ParsedArguments(ParsedArgument... arguments)
    {
        this(Arrays.asList(arguments));
    }

    public ParsedArguments(List<ParsedArgument> arguments)
    {
        this.arguments = arguments;
    }

    public ParsedArgument get(String key)
    {
        return arguments.stream()
                .filter(arg ->
                    arg.getFrom().getName().equals(key)
                ).findAny().orElse(null);
    }

    public String getContent(String key)
    {
        ParsedArgument arg = get(key);

        if (arg == null)
            throw new ArgumentNotPresentException("Argument not present: " + key);

        return arg.getContent();
    }

    @SuppressWarnings("unchecked")
    public <T> T getContentInType(String key)
        throws ArgumentNotPresentException
    {
        ParsedArgument arg = get(key);

        if (arg == null)
            throw new ArgumentNotPresentException("Argument not present: " + key);

        DataType type = arg.getFrom().getDataType();
        String content = arg.getContent();
        Object result = type.tryParse(content);

        if (result == null)
            throw new IllegalArgumentException("Invalid data type");

        return (T) result;
    }

    public List<ParsedArgument> getArguments()
    {
        return arguments;
    }
}