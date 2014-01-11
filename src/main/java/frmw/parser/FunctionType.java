package frmw.parser;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public enum FunctionType {

	AGGREGATION {
		@Override
		public List<String> functions(Parsing p) {
			return p.aggregationFunctions();
		}
	},

	SCALAR {
		@Override
		public List<String> functions(Parsing p) {
			return p.scalarFunctions();
		}
	},

	OLAP {
		@Override
		public List<String> functions(Parsing p) {
			return p.olapFunctions();
		}
	};

	public abstract List<String> functions(Parsing p);
}
