package frmw.model.util;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexey Paramonov
 */
public class IndexReplacer {

	private static class Holder implements Comparable<Holder> {

		public final int start;

		public final int finish;

		public final String replacement;

		private Holder(int start, int finish, String replacement) {
			this.start = start;
			this.finish = finish;
			this.replacement = replacement;
		}

		@Override
		public int compareTo(Holder o) {
			int res = ObjectUtils.compare(start, o.start);
			if (res == 0) {
				res = ObjectUtils.compare(finish, o.finish);
			}

			return res;
		}
	}

	private final String str;

	private final List<Holder> elements = new ArrayList<Holder>();

	public IndexReplacer(String str) {
		this.str = str;
	}

	public void add(int start, int finish, String replacement) {
		elements.add(new Holder(start, finish, replacement));
	}

	public String build() {
		Collections.sort(elements);

		StringBuilder sb = new StringBuilder();
		int prevIndex = 0;
		for (Holder h : elements) {
			sb.append(str, prevIndex, h.start)
					.append(h.replacement);
			prevIndex = h.finish;
		}

		sb.append(str, prevIndex, str.length());
		return sb.toString();
	}
}
