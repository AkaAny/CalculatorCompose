package com.example.calculatorcompose

import org.junit.Test

class ForTest {
    @Test
    fun forTest(){
        val stack=CalcStack()
        stack.add(CalcItem(ItemType.VALUE,1))
        stack.add(CalcItem(ItemType.OP,Op.ADD))
        stack.add(CalcItem(ItemType.VALUE,2))
        println(stack.action())
    }

//    fun sqlExecute(){
//        val statement="SELECT * FROM (some_table st INNER JOIN other_table ot) WHERE staff_id=1"
//        val resultSet=ArrayList<SomeTableWithOtherTable>()
//        for(line in linesInTable){
//            if(line.match(statement.whereClauseList)){
//                resultSet.add(line.filter(selectedColumnList))
//            }
//        }
//        resultSet.sort(orderByStatement)
//    }
//
//    fun match(line:Map<ColumnName,Value>,clauseList:List<Clause>):Boolean{
//        var matched=true
//        for(clause in clauseList){
//            if(line[clause.columnName]!=clause.value){
//                matched=false
//            }
//        }
//        return matched
//    }
}