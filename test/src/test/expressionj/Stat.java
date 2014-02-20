package test.expressionj;



import java.util.Arrays;




public class Stat implements Function {



  // ���

  public double sum(double[] number) {

      double result = 0;

      for (double num : number) {

          result += num;

      }

      return result;

  }



  // ��ƽ��ֵ

  public double average(double[] number) {

      double result = sum(number);

      result = result / number.length;

      return result;

  }



  // ����Сֵ

  public double min(double[] number) {

      double result = number[0];

      for (double num : number) {

          if (num < result) {

              result = num;

          }

      }

      return result;

  }



  // �����ֵ

  public double max(double[] number) {

      double result = number[0];

      for (double num : number) {

          if (num > result) {

              result = num;

          }

      }

      return result;

  }



  // ������ֵ�б�ĳ���

  public int count(double[] number) {

      return number.length;

  }



  // ���ش���scalar����ֵ�б�

  public double[] gt(double[] number, double scalar) {

      double[] result = new double[number.length];

      int count = 0;

      for (int i = 0; i < number.length; i++) {

          double num = number[i];

          if (num > scalar) {

              result[i] = num;

              count += 1;

          }

      }

      return Arrays.copyOf(result, count);

  }



  // ����С��scalar����ֵ�б�

  public double[] lt(double[] number, double scalar) {

      double[] result = new double[number.length];

      int count = 0;

      for (int i = 0; i < number.length; i++) {

          double num = number[i];

          if (num < scalar) {

              result[i] = num;

              count += 1;

          }

      }

      return Arrays.copyOf(result, count);

  }



  // ���ص���scalar����ֵ�б�

  public double[] eq(double[] number, double scalar) {

      double[] result = new double[number.length];

      int count = 0;

      for (int i = 0; i < number.length; i++) {

          double num = number[i];

          if (num == scalar) {

              result[i] = num;

              count += 1;

          }

      }

      return Arrays.copyOf(result, count);

  }



}
