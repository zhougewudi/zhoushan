package com.zhoushan.wenhua.suanfa;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class GenEntityMysql {
	Connection con = null;
	 Statement statement = null;
	 PreparedStatement prepare = null;
    private String packageOutPath;// 指定实体生成所在包的路径
    private String authorName;// 作者名字
    private String tablename;// 表名
    private String databasename;// 数据库名
    private List<String> tablenames;// 拿到对应数据库中所有的实体类（实体类需要与其他表明做区分）
    private List<String> colnames; // 列名集合
    private List<String> colTypes; // 列名类型集合
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*

    /*
     * 构造函数
     */
    public GenEntityMysql(String sjk,String sjb,String username,String password) throws ClassNotFoundException, SQLException {
        this.tablename = sjb;
        // 使用properties读取配置文件
 
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + sjk + "?serverTimezone=UTC", username, password);
//        this.databasename = sjk;
        this.packageOutPath = "com.zhouge.suanfa";
        this.authorName = "洲哥";
    }

    // 创建多个实体类
    public void genEntity(List<String> tablenames, Connection conn) {
        // 使用第归生成文件
        for (String tablename : tablenames) {
            this.genEntity(tablename, conn);
        }
    }

    // 创建单个实体类
    public void genEntity(String tablename, Connection conn) {
        String sql = "select * from " + tablename;
        PreparedStatement pstmt = null;
        ResultSetMetaData rsmd = null;
        try {
            pstmt = con.prepareStatement(sql);
            rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 统计列
            colnames = new ArrayList<String>();
            colTypes = new ArrayList<String>();

            for (int i = 0; i < size; i++) {
                colnames.add(rsmd.getColumnName(i + 1));
                colTypes.add(rsmd.getColumnTypeName(i + 1));

                if (colTypes.get(i).equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes.get(i).equalsIgnoreCase("image")
                        || colTypes.get(i).equalsIgnoreCase("text")) {
                    f_sql = true;
                }
            }
            System.out.println(colnames);
            System.out.println(colTypes);
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } finally {
           try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
        // 在内存中生成代码
        String content = parse(tablename);

        // 写入到文件中
        try {
            File directory = new File("");
            String outputPath = directory.getAbsolutePath() + "\\src\\"
                    + this.packageOutPath.replace(".", "\\") + "\\";
         //   String outputPath = directory.getAbsolutePath() + "\\src\\";
            System.out.println("写出的路径:" + outputPath);
            // 检测路径是否存在，不存在就创建路径
            File path = new File(outputPath);
            if (!path.exists() && !path.isDirectory()) {
                path.mkdir();
                System.out.println(path.exists());
            }
            // 创建文件
            outputPath += initcap(tablename) + ".java";
            File file = new File(outputPath);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 写出到硬盘
            FileWriter fw = new FileWriter(file);
            PrintWriter pw = new PrintWriter(fw);
         
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getAllEntityTable(Connection conn, List<String> tablenames) {
        ResultSet rs = null;
        try {
            DatabaseMetaData dmd = (DatabaseMetaData) conn.getMetaData();
            /*
             * TABLE_CAT String => 表类别（可为 null） 
             * TABLE_SCHEM String => 表模式（可为null） 
             * TABLE_NAME String => 表名称 
             * TABLE_TYPE String => 表类型
             */
            rs = dmd.getTables(null, null, "%", null);
            while (rs.next()) {
                tablenames.add(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @param tablename
     * @return
     */
    public String parse(String tablename) {
        StringBuffer sb = new StringBuffer();

        // 判断是否导入工具包
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        sb.append("package " + this.packageOutPath + ";\r\n");
        sb.append("\r\n");
        // 注释部分
        sb.append("   /**\r\n");
        sb.append("    * " + tablename + " 实体类\r\n");
        sb.append("    * " + new Date() + " " + this.authorName + "\r\n");
        sb.append("    */ \r\n");
        // 实体部分
        sb.append("\r\n\r\npublic class " + initcap(tablename) + "{\r\n");
        processAllAttrs(sb);// 属性
        processAllMethod(sb);// get set方法
        sb.append("}\r\n");

        return sb.toString();
    }

    /**
     * 功能：生成所有属性
     * 
     * @param sb
     */
    public void processAllAttrs(StringBuffer sb) {

        for (int i = 0; i < colnames.size(); i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes.get(i)) + " "
                    + colnames.get(i) + ";\r\n");
        }

    }

    /**
     * 功能：生成所有方法
     * 
     * @param sb
     */
    public void processAllMethod(StringBuffer sb) {

        for (int i = 0; i < colnames.size(); i++) {
            sb.append("\tpublic void set" + initcap(colnames.get(i)) + "("
                    + sqlType2JavaType(colTypes.get(i)) + " " + colnames.get(i)
                    + "){\r\n");
            sb.append("\t\tthis." + colnames.get(i) + "=" + colnames.get(i)
                    + ";\r\n");
            sb.append("\t}\r\n");
            sb.append("\tpublic " + sqlType2JavaType(colTypes.get(i)) + " get"
                    + initcap(colnames.get(i)) + "(){\r\n");
            sb.append("\t\treturn " + colnames.get(i) + ";\r\n");
            sb.append("\t}\r\n");
        }

    }

    /**
     * 功能：将输入字符串的首字母改成大写
     * 
     * @param str
     * @return
     */
    public String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 功能：获得列的数据类型
     * 
     * @param sqlType
     * @return
     */
    public String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("bit")) {
            return "boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "short";
        } else if (sqlType.equalsIgnoreCase("int")) {
            return "int";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real")
                || sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")
                || sqlType.equalsIgnoreCase("text")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        } else if (sqlType.equalsIgnoreCase("image")) {
            return "Blod";
        }

        return null;
    }

    /**
     * 出口 TODO
     * 
     * @param args
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
//    public static void main(String[] args) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
//
//        new GenEntityMysql().start();
//        Class clazz = Class.forName("com.zhouge.suanfa.Users");
//        Object object = clazz.newInstance();
//        System.out.println(object);
//
//    }

    public void start() {
        // 创建连接
        Connection conn = this.con;

        if (databasename != null && !databasename.equals("")
                && tablename != null && !tablename.equals("")) {
            System.out.println("databasename 和 tablename 不能同时存在");
        } else {
            // 如果配置文件中有数据库名字，则可以拿到其中所有的实体类
            if (databasename != null && !databasename.equals("")) {
                // 获取所有实体表名字
                tablenames = new ArrayList<String>();
                getAllEntityTable(conn, tablenames);
                System.out.println(tablenames);
                // 为每个实体表生成实体类
                genEntity(tablenames, conn);
            } else {
                // 为指定实体表生成实体类
                genEntity(tablename, conn);
            }

            // 关闭数据库连接
            if (conn != null) {
                try {
					conn.close();
					System.out.println("第一步");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }

    }

}
