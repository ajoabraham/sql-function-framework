package frmw.parser;

/**
 * @author Alexey Paramonov
 */
public enum HintType {

	SELECT {
		@Override
		public void parse(Parsing parser, String formula) {
			parser.parse(formula);
		}
	},
	JOIN {
		@Override
		public void parse(Parsing parser, String formula) {
			parser.parseJoin(formula);
		}
	};

	public abstract void parse(Parsing parser, String formula);
}
