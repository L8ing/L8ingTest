package test.expressionj;

import java.util.HashMap;
import java.util.Map;

import org.da.expressionj.expr.parser.EquationParser;
import org.da.expressionj.expr.parser.ParseException;
import org.da.expressionj.model.Equation;
import org.da.expressionj.model.Expression;
import org.da.expressionj.util.ExpressionCombiner;
import org.junit.Before;
import org.junit.Test;

public class testStat {

	private EquationUtil equationUtil;

	@Before
	public void before() throws InstantiationException, IllegalAccessException {

		equationUtil = new EquationUtil();

		equationUtil.register(Stat.class);

	}

	@Test
	public void f() throws ParseException {

		// ��������ֱ�Ӷ�ȡ

		String expr = "sum(a)";

		// ��ͨ�����ö�̬��ȡ

		Map<String, Object> o = new HashMap<String, Object>();

		o.put("a", new double[] { 1, 2, 3, 4, 5 });

		Object result = equationUtil.evaluate(expr, o);

		System.out.println(result);

	}

	@Test
	public void t() throws ParseException {

		String expr = "a + 1";

		Map<String, Object> o = new HashMap<String, Object>();

		o.put("a", 2);

		Object result = equationUtil.evaluate(expr, o);

		System.out.println(result);

	}

	@Test
	public void q() throws ParseException {

		String expr = "count(gt(a,0))";

		Map<String, Object> o = new HashMap<String, Object>();

		o.put("a", new double[] { 1, 2, 3, 4, 5 });

		Object result = equationUtil.evaluate(expr, o);

		System.out.println(result);

	}

	@Test
	public void r() throws ParseException {

		// ������ʽ�еı�������ظ�

		Equation condition = EquationParser.parse("sin(a)");

		Equation condition2 = EquationParser.parse("b + c");

		Expression expr = condition2.getExpression();

		ExpressionCombiner combiner = new ExpressionCombiner();

		Expression exprResult = combiner.replace(condition, "a", expr);

		exprResult.getVariable("b").setValue(1);

		exprResult.getVariable("c").setValue(2);
		System.out.println(" r : "+condition.eval());

	}
	
	@Test
	public void zz() throws ParseException {

		Equation condition = EquationParser.parse("sin(3)");

		System.out.println(" zz : "+condition.eval());

	}
	
	@Test
	public void z() throws ParseException {

		String expr = "a+b*c";

		Map<String, Object> o = new HashMap<String, Object>();

		o.put("a", 1);
		o.put("b", 2);
		o.put("c", 3);

		Object result = equationUtil.evaluate(expr, o);

		System.out.println(result);

	}
	


}
