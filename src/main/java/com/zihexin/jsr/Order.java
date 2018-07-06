package com.zihexin.jsr;

/**
 * Created by Administrator on 2018/6/27.
 * JSR 303基本的检验规则
 * <p>
 * 空检查
 * <p>
 * 长度检查
 * <p>
 * 日期检查
 */

/**
 * 空检查
 */
//@Null验证对象是否为null
//@NotNull验证对象是否不为null，无法检查长度为0的字符串
//@NotBlank 检查约束字符串是不是null还有被trim的长度是否大于0，只对字符串，且会去掉前后空格
//@NotEmptyString类，Collection、Map、数组，是不能为null或者长度为0的
//@AssertTrue验证Boolean对象是否为true
//@AssertFalse验证Boolean对象是否为false

/**
 * 长度检查
 */
//@Size(min=,max=)验证对象（Array,Collection,Map,String)长度是否在给定的范围之内
//@Length(min=,max=)验证字符串长度

/**
 * 日期检查
 */
//@Past验证Date和Calender对象是否在当前时间之前，验证成立的话被注释的元素一定是一个过去的日期
//@Future验证Date和Calender对象是否在当前时间之后
//@Pattern验证字符串对象是否符合正则表达式的规则 regexp:正则表达式，指定Pattern数组 message错误信息提示

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

/**
 * 数值检查
 * 建议使用在String，Integer类型，不建议使用在int类型上，因为表单值未""时无法转换为int，但可以转换为String为"",Integer为null
 */
//@Min验证Number和String对象是否大于等于指定的值
//@Max验证Number和String对象是否小于等于指定的值
//@DecimalMax被标注的值必须不大于约束中指定的最大值，这个约束的参数是BigDecimal定义的最大值得字符串表示，小数存在精度
//@DecimalMin同上
//@Digits（integer=,fraction=)验证字符串是否符合指定格式的数字,integer指
//
// 定整数精度，fraction指定小数精度
//@Range(min=,max=)被约束的值必须在指定的范围内
//@Valid递归的对关联对象进行校验，如果关联对象是集合或数组，那么对其中元素进行递归校验，如果是map，则对其中值进行校验
//@CreditCardNumber信用卡验证
//@Email验证是否是邮件地址，如果为null，不进行验证，算通过验证
//@URL(protocal=,host=,port=,regexp=,flags=)
public class Order {

    public void validateParams() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Object>> validations = validator.validate(this);
        if (validations.size() == 0) {
            return;
        } else {
            StringBuilder sb = new StringBuilder();
            for(ConstraintViolation validation :validations){
                sb.append(validation.getMessage()+" ");
            }
            throw new RuntimeException(sb.toString());
        }

    }
}
