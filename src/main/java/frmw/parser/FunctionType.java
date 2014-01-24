package frmw.parser;

import frmw.model.fun.FunctionSpec;

/**
 * @author Alexey Paramonov
 */
public enum FunctionType {

	AGGREGATION {
		@Override
		public Iterable<FunctionSpec> functions(Parsing p) {
			return p.registry().aggregation();
		}
	},

	SCALAR {
		@Override
		public Iterable<FunctionSpec> functions(Parsing p) {
			return p.registry().scalar();
		}
	},

	OLAP {
		@Override
		public Iterable<FunctionSpec> functions(Parsing p) {
			return p.registry().olap();
		}
	};

	public abstract Iterable<FunctionSpec> functions(Parsing p);
}
