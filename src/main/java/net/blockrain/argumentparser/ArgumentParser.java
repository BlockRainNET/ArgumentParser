package net.blockrain.argumentparser;

import java.util.*;

public class ArgumentParser
{
    private final List<String> arguments;

    public ArgumentParser(String... arguments)
    {
        this(Arrays.asList(arguments));
    }

    public ArgumentParser(List<String> arguments)
    {
        this.arguments = arguments;
    }

    public ParsedArguments parse(ParseOptions options)
    {
        return new ParsedArguments(parseRaw(options));
    }

    public List<ParsedArgument> parseRaw(ParseOptions options)
        throws NotEnoughArgumentsException, IllegalArgumentException, ArgumentNotPresentException
    {
        Deque<String> argumentQueue = new ArrayDeque<>(arguments);
        List<ParsedArgument> parsed = new ArrayList<>();
        String nextArg;

        while ((nextArg = argumentQueue.poll()) != null)
        {
            if (!nextArg.startsWith("-"))
                throw new IllegalArgumentException("Invalid argument start: " + nextArg);

            String possiblyNext = nextArg.substring(1);

            options.getArguments().stream().filter(arg -> arg.getName().equals(possiblyNext)).forEach(arg -> {
                int expectedLength = arg.getSplitLength(), current = 0;
                DataType dataType = arg.getDataType();
                StringBuilder content = new StringBuilder();

                while (current != expectedLength)
                {
                    try
                    {
                        String next = argumentQueue.remove();

                        if (next.startsWith("-"))
                        {
                            argumentQueue.push(next);

                            break;
                        }

                        if (!dataType.isApplicable(next))
                            throw new IllegalArgumentException("Data type " + dataType + " is not applicable for " + next);

                        content.append(next);
                    } catch (NoSuchElementException ex)
                    {
                        throw new NotEnoughArgumentsException();
                    }

                    ++current;
                }

                parsed.add(new ParsedArgument(arg, content.toString()));
            });
        }

        options.getArguments()
                .stream()
                .filter(Argument::isOptional)
                .filter(arg ->
                        parsed.stream().noneMatch(par -> par.getFrom().hashCode() == arg.hashCode()))
                .filter(arg -> arg.getDefault() != null)
                .forEach(arg -> parsed.add(new ParsedArgument(arg, arg.getDefault())));

        options.getArguments()
                .stream()
                .filter(arg -> !arg.isOptional())
                .filter(arg ->
                        parsed.stream().noneMatch(par -> par.getFrom().hashCode() == arg.hashCode()))
                .forEach(arg -> {
            throw new ArgumentNotPresentException("Argument not found: " + arg.getName());
        });

        return parsed;
    }
}