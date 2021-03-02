package net.blockrain.argumentparser;

import java.nio.file.Paths;

public enum DataType
{
    STRING {
        @Override
        public Object tryParse(String data)
        {
            return data;
        }

        @Override
        public boolean isApplicable(String data)
        {
            return true;
        }
    },
    INT {
        @Override
        public Object tryParse(String data)
        {
            return Integer.parseInt(data);
        }

        @Override
        public boolean isApplicable(String data)
        {
            return data.matches("[-+]?[0-9]++");
        }
    },
    FLOAT {
        @Override
        public Object tryParse(String data)
        {
            return Float.parseFloat(data);
        }

        @Override
        public boolean isApplicable(String data)
        {
            return data.matches("[-+]?[0-9]*\\.?[0-9]+");
        }
    },
    BOOLEAN {
        @Override
        public Object tryParse(String data)
        {
            return Boolean.parseBoolean(data);
        }

        @Override
        public boolean isApplicable(String data)
        {
            return data.matches("(?i)(true|false)");
        }
    },
    FILE {
        @Override
        public Object tryParse(String data)
        {
            return Paths.get(data);
        }

        @Override
        public boolean isApplicable(String data)
        {
            return true;
        }
    }
    ;

    public /* abstract */ Object tryParse(String data)
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    public /* abstract */ boolean isApplicable(String data)
        throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }
}