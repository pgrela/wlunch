package com.pgrela.wlunch.utils;

public class MenuBuilder {
    private StringBuilder builder = new StringBuilder();

    public MenuBuilder appendMenu(String menu) {
        return this.append(indent(menu)).append("\n\n");
    }

    public MenuBuilder appendHeader(String restaurant) {
        return this.append(restaurant).append(":\n\t");
    }

    private MenuBuilder append(String string) {
        builder.append(string);
        return this;
    }

    private String indent(String paragraph) {
        return paragraph.replaceAll("\n", "\n\t");
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
