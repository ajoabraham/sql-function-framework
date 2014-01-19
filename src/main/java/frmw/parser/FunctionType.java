package frmw.parser;

import frmw.model.hint.FunctionSpec;

import java.util.List;

/**
 * @author Alexey Paramonov
 */
public enum FunctionType {

	AGGREGATION {
		@Override
		public List<FunctionSpec> functions(Parsing p) {
			return p.aggregationFunctions();
		}
	},

	SCALAR {
		@Override
		public List<FunctionSpec> functions(Parsing p) {
			return p.scalarFunctions();
		}
	},

	OLAP {
		@Override
		public List<FunctionSpec> functions(Parsing p) {
			return p.olapFunctions();
		}
	};

	public abstract List<FunctionSpec> functions(Parsing p);
}
