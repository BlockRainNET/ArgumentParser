package net.blockrain.argumentparser;

public class ArgumentNotPresentException
    extends IllegalStateException
{
    public ArgumentNotPresentException(String message)
    {
        super(message);
    }
}