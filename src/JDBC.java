import java.sql.*;

public class JDBC {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    //连接数据库方法
    public Connection getConnection(){
        String url = "jdbc:mysql://localhost:端口/数据库";
        String userName = "root";
        String password="密码";
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("找不到驱动！");
            e.printStackTrace();
        }

        try{
            conn = DriverManager.getConnection(url,userName,password);
            if(conn!=null){
                System.out.println("connection successful");
            }
        }catch (SQLException e){
            System.out.println("connection fail");
            e.printStackTrace();
        }
        return conn;
    }

    //通过姓名查询用户并返回用户
    public User QuerySql(String username){
        System.out.println("query");
        User user = new User();
        String sql = "select * from user where name like "+username;
        try{
            conn = getConnection();//连接数据库
            ps=conn.prepareStatement(sql);//创建Statement并执行参数
            rs = ps.executeQuery(sql);//执行sql语句
            while(rs.next()){
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setState(rs.getString("state"));
                user.setStartTime(rs.getString("startTime"));
                user.setEndTime(rs.getString("endTime"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {//释放资源
            try{
                rs.close();
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return user;
    }

    //添加用户并返回行号
    @SuppressWarnings("null")
    public int add(User user){
        int row = 0;

        String sql = "insert into user(name,password) values (?,?)";
        try{
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);//创建Statement并设置参数

            ps.setString(1,user.getName());
            ps.setString(2,user.getPassword());

            //处理结果集
            row = ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {//释放资源
            try{
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return row;
    }

    //更改用户信息
    public void update(User user){
        String sql = "update user set state=?,startTime=?,endTime=? where name=?";

        try {
                conn=getConnection();//连接数据库
                ps=conn.prepareStatement(sql);//创建Statement并设置参数

                ps.setString(1,user.getState());
                ps.setString(2,user.getStartTime());
                ps.setString(3,user.getEndTime());
                ps.setString(4,user.getName());

            }catch (SQLException e){
                e.printStackTrace();
            }finally {//释放资源
                try{
                    ps.close();
                    conn.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

    }

    //删除
    public void delete(User user){
        String sql = "delete from user where name=?";

        try{
            conn=getConnection();//连接数据库
            ps=conn.prepareStatement(sql);//创建Statement并设置参数

            ps.setString(1,user.getName());

        }catch (SQLException e){
            e.printStackTrace();
        }finally {//释放资源
            try{
                ps.close();
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String args[]){
        JDBC jdbc = new JDBC();
        jdbc.QuerySql("");
    }
}










