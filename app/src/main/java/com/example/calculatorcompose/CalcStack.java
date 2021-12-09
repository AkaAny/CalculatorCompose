package com.example.calculatorcompose;

import android.content.Context;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class CalcStack {
    private final Queue<CalcItem> items=new LinkedList<>();

    public void add(CalcItem item){
        CalcItem last=items.peek();
        if(last.getType()==ItemType.VALUE){
            String newValueStr= last.getValue().toString()+item.getValue().toString();
            int value= Integer.parseInt(newValueStr);
            items.add(new CalcItem(ItemType.VALUE,value));
        }
        items.add(item);
    }

    public int action() throws IllegalArgumentException{
        int result=0;
        CalcItem last=null;
        int index=0;
        while (!items.isEmpty()){
            CalcItem current= items.poll();
            if(current==null){
                break;
            }
            if(last==null&&current.getType()==ItemType.OP){
                throw new InvalidInputException("op cannot be first",index);
            }
            if(current.getType()==ItemType.OP){
                last=current;
                current=items.poll();
                Op op=(Op) last.getValue();
                int value= (int) current.getValue();
                switch (op){
                    case ADD:
                        result+=value;
                        break;
                    case SUB:
                        result-=value;
                        break;
                }
            }
            last=current;
            index++;
        }
        return result;
    }
}
