package net.blockrain.argumentparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseOptions
{
    private final List<Argument> arguments;

    public ParseOptions(Argument... arguments)
    {
        this(Arrays.asList(arguments));
    }

    public ParseOptions(List<Argument> arguments)
    {
        this.arguments = arguments;
    }

    public List<Argument> getArguments()
    {
        return arguments;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private Builder() {}

        private final List<Argument> arguments = new ArrayList<>();

        public Builder argument(Argument argument)
        {
            arguments.add(argument);

            return this;
        }

        public Builder argument(Argument.Builder builder)
        {
            return argument(builder.build());
        }

        public ParseOptions build()
        {
            return new ParseOptions(arguments);
        }
    }
}