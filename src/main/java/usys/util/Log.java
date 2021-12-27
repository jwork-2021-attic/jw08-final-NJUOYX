package usys.util;

public class Log {
    static public void log(String string){
        System.out.println("Error at" + getTraceInfo(2)+"Error Info: " +string);
    }
    public static String getTraceInfo(int depth) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        int stacksLen = stacks.length;
        sb.append("class: ").append(stacks[depth].getClassName())
                .append("; method: ").append(stacks[depth].getMethodName())
                .append("; number: ").append(stacks[depth].getLineNumber());
        return sb.toString();
    }
}
