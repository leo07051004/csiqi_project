package com.csiqi.utils.test;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cl_kd-user47823 on 2019/11/12.
 */
public class TestLambda {
    public static void  main(String [] args){
        List<Stationery> a= Lists.newArrayList(
                new Stationery("閽㈢瑪","绾㈡湰")
                ,new Stationery("閽㈢瑪","鏈瓙"));
        List<Stationery> b=Lists.newArrayList(
                new Stationery("鍦嗙彔绗�","缁挎湰"));
        List<Stationery> c=Lists.newArrayList(
                new Stationery("閽㈢瑪","钃濆瓙"),
                new Stationery("閾呯瑪","榛勬湰"));
        List<Student> students=new ArrayList<>();
        students.add(new Student("001","绋嬪簭姹�","鐢�", a,18));
        students.add(new Student("002","灏忕孩","濂�", b,11));
        students.add(new Student("003","灏忕編","濂�", c,10));

        Optional.ofNullable(students).orElse(Collections.emptyList()).forEach(System.out::println);
        Optional<String> oFinFirst=students.stream().filter(Objects:: nonNull).map(Student::getName).filter(f-> StringUtils.equals(f,"绋嬪簭姹�")).findFirst();
        if(oFinFirst.isPresent()){
            System.out.println("缁�"+oFinFirst.get()+"鍙戯紒锛�");
        }
        oFinFirst.ifPresent(fs->{System.out.println("缁�"+fs+"鍙戝彂锛�");});
        students.stream().filter(Objects::nonNull).forEach(f->System.out.println(f.getName()));
        List<String>names=students.stream().filter(Objects :: nonNull).map(Student::getSex).collect(Collectors.toList());
        names.forEach(System.out::println);
        //students.stream().filter(Objects::nonNull).map(Student::getStationerys).flatMap().forEach(System.out::println);
        Map<String,Student>map=students.stream().filter(Objects::nonNull).collect(Collectors.toMap(Student::getStudentId,s->s));
        System.out.println(JSON.toJSON(map));
        Map<String,List<Student>>map2=students.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(stu->stu.getSex()));
        System.out.println(JSON.toJSON(map2));
        List<String> names2=students.stream().filter(Objects::nonNull).map(Student::getName).skip(0).limit(3).collect(Collectors.toList());
        names2.forEach(System.out::println);
        Collections.sort(students,Comparator.comparing(Student::getAge));
        students.forEach(f->System.out.println(f.getAge()));
    }
}
