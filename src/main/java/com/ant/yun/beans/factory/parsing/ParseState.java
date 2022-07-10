package com.ant.yun.beans.factory.parsing;

import com.ant.yun.lang.Nullable;

import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * @author jack_yun
 * @version 1.0
 * @description: TODO
 * @date 2022/7/9 15:35
 */
public final class ParseState {
    private final ArrayDeque<Entry> state;

    public ParseState() {
        this.state = new ArrayDeque();
    }

    private ParseState(ParseState other) {
        this.state = other.state.clone();
    }

    public void push(ParseState.Entry entry) {
        this.state.push(entry);
    }

    public void pop() {
        this.state.pop();
    }

    @Nullable
    public ParseState.Entry peek() {
        return (ParseState.Entry)this.state.peek();
    }

    public ParseState snapshot() {
        return new ParseState(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        int i = 0;

        for(Iterator var3 = this.state.iterator(); var3.hasNext(); ++i) {
            ParseState.Entry entry = (ParseState.Entry)var3.next();
            if (i > 0) {
                sb.append('\n');

                for(int j = 0; j < i; ++j) {
                    sb.append('\t');
                }

                sb.append("-> ");
            }

            sb.append(entry);
        }

        return sb.toString();
    }

    public interface Entry {
    }
}
