package be.alexandre01.inazuma.uhc.timers.exception;

import be.alexandre01.inazuma.uhc.InazumaUHC;
import be.alexandre01.inazuma.uhc.config.Messages;

public class NullTimerException extends Exception {
    public NullTimerException(String msg){
        super(msg);
    }
}
