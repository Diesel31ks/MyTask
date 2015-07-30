package mytask;

import java.util.ArrayList;
import java.util.Iterator;

public class StringArrayList extends ArrayList<String> {
	private static final long serialVersionUID = 4065260109640939791L;

	@Override
	public String toString() {
        Iterator<String> it = iterator();
        if (! it.hasNext())
            return "";
        StringBuilder sb = new StringBuilder();
        for (;;) {
            String str = it.next();
            sb.append(str);
            if (! it.hasNext())
                return sb.toString();
        }
	}
	
}
