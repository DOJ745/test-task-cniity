package com.example.springbootdemo.structs;

public enum MsgTypes
{
    MSG_ERROR ("error"),
    MSG_BAD_REQUEST ("bad request"),
    MSG_NOT_FOUND ("not found"),
    MSG_INTERNAL_SERVER_ERROR ("internal server error"),
    MSG_SUCCESS ("success");

    private final String msgType;

    MsgTypes(String msgType)
    {
        this.msgType = msgType;
    }

    public static MsgTypes fromString(String msgType)
    {
        for (MsgTypes type : MsgTypes.values())
        {
            if (type.msgType.equalsIgnoreCase(msgType))
            {
                return type;
            }
        }

        return null;
    }

    @Override
    public String toString()
    {
        return msgType;
    }
}
