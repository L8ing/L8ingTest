package test.expressionj;

import org.da.expressionj.expr.parser.EquationParser;

import org.da.expressionj.expr.parser.ParseException;

import org.da.expressionj.functions.FunctionsDefinitions;

import org.da.expressionj.model.Equation;

import org.da.expressionj.model.Variable;

import java.lang.reflect.Method;

import java.util.HashMap;

import java.util.Map;

public class EquationUtil {

	private final Map<String, Equation> equations;

	private static FunctionsDefinitions def;

	public EquationUtil() {

		equations = new HashMap<String, Equation>();

		def = FunctionsDefinitions.getInstance();

	}

	/**
	 * 
	 * ע�ắ��
	 */

	public void register(Class<? extends Function> clz)
			throws IllegalAccessException, InstantiationException {

		Function func = clz.newInstance();

		Method[] methods = clz.getMethods();

		for (Method method : methods) {

			def.addFunction(method.getName(), func, method);

		}

	}

	/**
	 * 
	 * �ͷ�ע��ĺ���
	 */

	public static void reset() {

		def.reset();

	}

	/**
	 * 
	 * ��ݱ��ʽ����÷���ʽ����
	 */

	public Equation getEquation(String expr) throws ParseException {

		Equation equation = equations.get(expr);

		if (equation == null) {

			equation = EquationParser.parse(expr);

			equations.put(expr, equation);

		}

		return equation;

	}

	/**
	 * 
	 * @param expr
	 *            ���ʽ���
	 * 
	 * @param o
	 *            ��������ļ�ֵ���б�,���������ʽ�в�������ͬ
	 */

	public Object evaluate(String expr, Map<String, Object> o)
			throws ParseException {

		Equation equation = getEquation(expr);

		Map<String, Variable> vars = equation.getVariables();

		for (String key : vars.keySet()) {

			vars.get(key).setValue(o.get(key));

		}

		return equation.eval();

	}

}
