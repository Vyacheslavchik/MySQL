package com.digdes.school;

import java.util.*;
/**
 * ����� JavaSchoolStarter - ���������� ���������� ������� ����� ���������� ������� � ���������.
 * @author ������ �.�.
 */
public class JavaSchoolStarter {
    /**���� ��� �������� ������� � ������ ID,LASTNAME,COST,AGE,ACTIVE*/
    private List<Map<String,Object>> table = new ArrayList<Map<String, Object>>();
    /**��������� ����������� ������ {@link JavaSchoolStarter}*/
    public JavaSchoolStarter() {

    }
    /**�� ���� �������� ������, �� ������ ���������, ���������� ����� ���������� ������� �������
     * @param request sql ������
     * @return ���������, ���������� ����� ���������� ������� */
    public List<Map<String,Object>> execute(String request) throws Exception {
        Komands komands = new Komands(request);
        switch (komands.keyWord){
            case "SELECT":
                if(komands.whereUslovie==null){
                    return table;
                }else{
                    return select(komands.whereUslovie);
                }
            case "UPDATE":
                if(komands.whereUslovie==null){
                    return update(komands.valueUslovie);
                }else{
                    return updateWhere(komands.valueUslovie,komands.whereUslovie);
                }
            case "DELETE":
                if(komands.whereUslovie==null){
                    table.clear();
                    return table;
                }else{
                    return delete(komands.whereUslovie);
                }
            case "INSERT":
                return insert(komands.valueUslovie);
        }
        return new ArrayList<>();
    }
    /**���������� ��� ������ �������� ������� � ������ � ��� �������� �� ������� values
     * @param values ������ � ��������� ��� ������ � ������� "���� �������� ���� ��������..."
     * @return �������� ������� � ������� List<Map<String,Object>> � ������������ �������� */
    private List<Map<String,Object>> update(String values){
        String[] arrayRequest=values.split(" ");
        for(int numberRow=0;numberRow<table.size();numberRow++){
            Map<String,Object> row = table.get(numberRow);
            for(int i=0;i<arrayRequest.length;i+=2){
                switch (arrayRequest[i]) {
                    case "ID":
                        if (!arrayRequest[i + 1].equals("null")){
                            row.put("ID", Long.parseLong(arrayRequest[i + 1]));
                        }else{
                            row.put("ID",null);
                        }
                        break;
                    case "LASTNAME":
                        if (!arrayRequest[i + 1].equals("null"))
                            row.put("LASTNAME", arrayRequest[i + 1]);
                        else
                            row.put("LASTNAME", null);
                        break;
                    case "COST":
                        if (!arrayRequest[i + 1].equals("null"))
                            row.put("COST", Double.parseDouble(arrayRequest[i + 1]));
                        else
                            row.put("COST", null);
                        break;
                    case "AGE":
                        if (!arrayRequest[i + 1].equals("null"))
                            row.put("AGE", Long.parseLong(arrayRequest[i + 1]));
                        else
                            row.put("AGE", null);
                        break;
                    case "ACTIVE":
                        if (!arrayRequest[i + 1].equals("null"))
                            row.put("ACTIVE", Boolean.parseBoolean(arrayRequest[i + 1]));
                        else
                            row.put("ACTIVE", null);
                        break;
                }
            }
            table.set(numberRow,row);
        }
        return table;
    }
    /**���������� ��� ������ �������� ������� � ������ �������� �� ������� values � ��� �� ���, ������� ������������� ������� uslovieWhere
     * @param values ������ � ��������� ��� ������ � ������� "���� �������� ���� ��������..."
     * @param uslovieWhere ������ � �������� ����� where ������� "����=��������AND����=��������..."
     * @return �������� ������� � ������� List<Map<String,Object>> � ������������ �������� */
    private List<Map<String,Object>> updateWhere(String values,String uslovieWhere) throws Exception {
        String[] arrayRequest=values.split(" ");
        for(int numberRow=0;numberRow<table.size();numberRow++){
            if (where(uslovieWhere,table.get(numberRow))) {
                Map<String, Object> row = table.get(numberRow);
                for (int i = 0; i < arrayRequest.length; i += 2) {
                    switch (arrayRequest[i]) {
                        case "ID":
                            if (!arrayRequest[i + 1].equals("null")){
                                row.put("ID", Long.parseLong(arrayRequest[i + 1]));
                            }else{
                                row.put("ID",null);
                            }
                            break;
                        case "LASTNAME":
                            if (!arrayRequest[i + 1].equals("null"))
                                row.put("LASTNAME", arrayRequest[i + 1]);
                            else
                                row.put("LASTNAME", null);
                            break;
                        case "COST":
                            if (!arrayRequest[i + 1].equals("null"))
                                row.put("COST", Double.parseDouble(arrayRequest[i + 1]));
                            else
                                row.put("COST", null);
                            break;
                        case "AGE":
                            if (!arrayRequest[i + 1].equals("null"))
                                row.put("AGE", Long.parseLong(arrayRequest[i + 1]));
                            else
                                row.put("AGE", null);
                            break;
                        case "ACTIVE":
                            if (!arrayRequest[i + 1].equals("null"))
                                row.put("ACTIVE", Boolean.parseBoolean(arrayRequest[i + 1]));
                            else
                                row.put("ACTIVE", null);
                            break;
                        default:throw new Exception();
                    }
                }
                table.set(numberRow, row);
            }
        }
        return table;
    }
    /**������� �� �������� ������� ������ ��������������� ������������ �������
     * @param request ������ � �������� ����� where ������� "����=��������AND����=��������..."
     * @return �������� ������� � ������� List<Map<String,Object>> ����� �������� ����������� ����� */
    private List<Map<String,Object>> delete(String request) throws Exception {
        for(int numberRow=0;numberRow<table.size();numberRow++){
            if(where(request,table.get(numberRow))){
                table.remove(numberRow);
                numberRow--;
            }
        }
        return table;
    }
    /**��������� � �������� ������� ����� ������
     * @param request ������ � �������� ������� "���� �������� ���� ��������..."
     * @return �������� ������� � ������� List<Map<String,Object>> � ����������� ������� */
    private List<Map<String,Object>> insert(String request) throws Exception {
        Map<String,Object> row = new HashMap<>();
        String[] arrayRequest=request.split(" ");
        for(int i=0;i<arrayRequest.length;i+=2){
            switch (arrayRequest[i]){
                case "ID":
                    if(!arrayRequest[i+1].equals("null"))
                        row.put("ID", Long.parseLong(arrayRequest[i + 1]));
                    break;
                case "LASTNAME":
                    if(!arrayRequest[i+1].equals("null"))
                        row.put("LASTNAME",arrayRequest[i+1]);
                    break;
                case "COST":
                    if(!arrayRequest[i+1].equals("null"))
                        row.put("COST",Double.parseDouble(arrayRequest[i+1]));
                    break;
                case "AGE":
                    if(!arrayRequest[i+1].equals("null"))
                        row.put("AGE",Long.parseLong(arrayRequest[i+1]));
                    break;
                case "ACTIVE":
                    if(!arrayRequest[i+1].equals("null"))
                        row.put("ACTIVE",Boolean.parseBoolean(arrayRequest[i+1]));
                    break;
                default:throw new Exception();
            }
        }
        table.add(row);
        return table;
    }
    /**�������� �� �������� ������� ������, ������� �������� ������� ����� where
     * @param uslovie ��������� ��������������� ������ � �������� ����� where
     * @return �������� �� ���������� ����� �� ����� �������*/
    private List<Map<String,Object>> select(String uslovie) throws Exception {
        List<Map<String,Object>> newTable = new ArrayList<Map<String, Object>>();
        for(int row=0;row<table.size();row++){
            if(where(uslovie,table.get(row))) newTable.add(table.get(row));
        }
        return newTable;
    }

    /**��������� ������� where �� ������� � ���������� ��� ���������� ������������� � ���������� ������
     * @param uslovie ��������� ��������������� ������ � �������� ����� where
     * @param row ��������� ��������� map, ������� �������� ������� �������-���������
     * @return true, ���� ������ row ������������� ������� uslovie"*/
    private boolean where(String uslovie, Map<String,Object> row) throws Exception {
        if(uslovie.indexOf("OR")!=-1)
            return or(uslovie,row);
        else if(uslovie.indexOf("AND")!=-1)
            return and(uslovie,row);
        else
            return iziFunkc(uslovie,row);
    }
    private boolean or(String uslovie,Map<String,Object> row) throws Exception {
        String[]newUslonie = uslovie.split("OR");
        for(int i=0;i<newUslonie.length;i++){
            if(newUslonie[i].indexOf("AND")!=-1) {
                if (and(newUslonie[i], row)) return true;
            }else {
                if (iziFunkc(newUslonie[i], row)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean and(String uslovie,Map<String,Object> row) throws Exception {
        String[]newUslonie = uslovie.split("AND");
        for(int i=0;i<newUslonie.length;i++) {
            if (!iziFunkc(newUslonie[i], row)) return false;
        }
        return true;
    }
    private boolean iziFunkc(String uslovie,Map<String,Object> row) throws Exception {
        if (uslovie.indexOf("!=") != -1) {
            String one = uslovie.split("!=")[0].toUpperCase(Locale.ROOT);
            String two = uslovie.split("!=")[1];
            if(row.get(one)==null)return false;
            if (one.equals("ID") || one.equals("AGE")) {
                return Long.parseLong(String.valueOf(row.get(one))) != Long.parseLong(two);
            } else if (one.equals("COST")) {
                return Double.parseDouble(String.valueOf(row.get(one))) != Double.parseDouble(two);
            } else {
                return !String.valueOf(row.get(one)).equals(two);
            }
        }
        if (uslovie.indexOf("ILIKE") != -1) {
            String one = uslovie.split("ILIKE")[0].toUpperCase(Locale.ROOT);
            String twoUsl= uslovie.split("ILIKE")[1].toUpperCase(Locale.ROOT);
            String two = twoUsl.replace("%","");
            if (!one.equals("LASTNAME")) throw new Exception();
            if(row.get(one)==null)return false;

            if(String.valueOf(row.get(one)).toUpperCase(Locale.ROOT).indexOf(two) != -1) {

                if (twoUsl.startsWith("%") & twoUsl.endsWith("%")) {
                    return true;
                } else if (twoUsl.startsWith("%")) {
                    return String.valueOf(row.get(one)).toUpperCase(Locale.ROOT).endsWith(two);
                } else if (twoUsl.endsWith("%")) {
                    return  String.valueOf(row.get(one)).toUpperCase(Locale.ROOT).startsWith(two);
                } else {
                    return String.valueOf(row.get(one)).toUpperCase(Locale.ROOT).equals(two);
                }
            }
            return false;
        }
        if (uslovie.indexOf("LIKE") != -1) {
            String one = uslovie.split("LIKE")[0].toUpperCase(Locale.ROOT);
            String twoUsl= uslovie.split("LIKE")[1];
            String two = twoUsl.replace("%","");
            if (!one.equals("LASTNAME")) throw new Exception();
            if(row.get(one)==null)return false;

            if(String.valueOf(row.get(one)).indexOf(two) != -1) {
                if (twoUsl.startsWith("%") & twoUsl.endsWith("%")) {
                    return true;
                } else if (twoUsl.startsWith("%")) {
                    return String.valueOf(row.get(one)).endsWith(two);
                } else if (twoUsl.endsWith("%")) {
                    return  String.valueOf(row.get(one)).startsWith(two);
                } else {
                    return String.valueOf(row.get(one)).equals(two);
                }
            }
            return false;
        }

        if (uslovie.indexOf(">=") != -1) {
            String one = uslovie.split(">=")[0].toUpperCase(Locale.ROOT);
            String two = uslovie.split(">=")[1];
            if(row.get(one)==null)return false;
            if (one.equals("ID") || one.equals("AGE")) {
                return Long.parseLong(String.valueOf(row.get(one))) >= Long.parseLong(two);
            } else
                return Double.parseDouble(String.valueOf(row.get(one))) >= Double.parseDouble(two);
        }
        if (uslovie.indexOf("<=") != -1) {
            String one = uslovie.split("<=")[0].toUpperCase(Locale.ROOT);
            String two = uslovie.split("<=")[1];
            if(row.get(one)==null)return false;
            if (one.equals("ID") || one.equals("AGE")){
                return Long.parseLong(String.valueOf(row.get(one))) <= Long.parseLong(two);
            } else
                return Double.parseDouble(String.valueOf(row.get(one))) <= Double.parseDouble(two);
        }
        if(uslovie.indexOf("<")!=-1){
            String one = uslovie.split("<")[0].toUpperCase(Locale.ROOT);
            String two = uslovie.split("<")[1];
            if(row.get(one)==null)return false;
            if (one.equals("ID") || one.equals("AGE")){
                return Long.parseLong(String.valueOf(row.get(one))) < Long.parseLong(two);
            } else
                return Double.parseDouble(String.valueOf(row.get(one))) < Double.parseDouble(two);
        }
        if(uslovie.indexOf(">")!=-1){
            String one = uslovie.split(">")[0].toUpperCase(Locale.ROOT);
            String two = uslovie.split(">")[1];
            if(row.get(one)==null)return false;
            if (one.equals("ID") || one.equals("AGE")){
                return Long.parseLong(String.valueOf(row.get(one))) > Long.parseLong(two);
            } else
                return Double.parseDouble(String.valueOf(row.get(one))) > Double.parseDouble(two);
        }
        if(uslovie.indexOf("=")!=-1){//�������� ��� ������ �����
            String one = uslovie.split("=")[0].toUpperCase(Locale.ROOT);
            String two = uslovie.split("=")[1];
            if(row.get(one)==null)return false;
            if (one.equals("ID") || one.equals("AGE")) {
                return Long.parseLong(String.valueOf(row.get(one))) == Long.parseLong(two);
            } else if (one.equals("COST")) {
                return Double.parseDouble(String.valueOf(row.get(one))) == Double.parseDouble(two);
            } else {
                return String.valueOf(row.get(one)).equals(two);
            }
        }
        return false;
    }


    /**
     * ����� Komands - ��������� ����� ������ {@link JavaSchoolStarter}; ������ sql ������, ������� �������� �� �������� �����, �������, ������� where
     * @author ������ �.�.
     */
    private class Komands{
        /**���� ��� ��������� sql-�������*/
        String keyWord;
        /**���� ��� �������� ������� ������� � ������� "���� �������� ���� ��������..."*/
        String valueUslovie;
        /**���� ��� �������� ������� where � ������� "����=��������AND����=��������..."*/
        String whereUslovie;
        /**������� ����� � sql �������� ����������� �� �����-������, ������� �������, ������� ����� where
         * @param line - ������ c sql ��������*/
        public Komands(String line){
            line=line.trim();
            if(line.indexOf(" ")==-1){
                keyWord=line.toUpperCase(Locale.ROOT);
            }else {
                keyWord = line.split(" ")[0].toUpperCase(Locale.ROOT);
                line=line.split(line.split(" ")[1])[1];
                if (keyWord.equals("SELECT") || keyWord.equals("DELETE")) {
                    whereUslovie = upRegistrKeyValueForWhere(line);
                }else if(keyWord.equals("INSERT")){
                    valueUslovie = upRegistrKeyValueForValue(line);
                }else{
                    if(line.toUpperCase(Locale.ROOT).indexOf("WHERE")==-1){
                        valueUslovie = upRegistrKeyValueForWhere(line);
                    }else{
                        String where = line.indexOf("WHERE")==-1 ? "where" : "WHERE";
                        valueUslovie = line.split(where)[0];
                        whereUslovie = upRegistrKeyValueForWhere(line.split(where)[1]);
                    }
                    valueUslovie=upRegistrKeyValueForValue(valueUslovie);
                }
            }

        }
        /**����������� ������ ��������� �������
         * @param request ��������� ������ ������� � ������� ���������, ��������
         *  @return �������� ������-������ � ������� "����=�������� ����=��������..."*/
        private String upRegistrKeyValueForValue(String request){
            request=request.replaceAll("'","");
            String[]strings = request.split(",");
            request="";
            for(int i=0;i<strings.length;i++){
                String[] pajes = strings[i].split("=");
                for(int q=0;q<pajes.length;q++){
                    request+=pajes[q].trim() +" ";
                }
            }
            request=request.trim();
            String [] arrayLine = request.split(" ");
            request="";
            for(int i=0;i<arrayLine.length;i++){
                request+= (i%2==0 ? arrayLine[i].toUpperCase(Locale.ROOT) : arrayLine[i])+" ";
            }
            return request.trim();
        }
        /**����������� ������ ����� WHERE
         * @param request ��������� ������ ������� � ������� ���������, ��������
         *  @return �������� ������-������ � ������� "����=��������AND����=��������..."*/
        private String upRegistrKeyValueForWhere(String request){
            request=request.replaceAll("'","").trim();
            String[]strings = request.split(" ");
            String[]bigStrings=request.toUpperCase(Locale.ROOT).split(" ");
            request="";
            for(int i=0;i<bigStrings.length;i++){
                if(bigStrings[i].equals("AND")) strings[i]="AND";
                if(bigStrings[i].equals("OR")) strings[i]="OR";
                if(bigStrings[i].equals("LIKE")) strings[i]="LIKE";
                if(bigStrings[i].equals("ILIKE")) strings[i]="ILIKE";
                request+=strings[i];
            }
            return request;
        }

    }
}
