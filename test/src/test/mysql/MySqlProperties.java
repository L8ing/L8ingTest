package test.mysql;

public class MySqlProperties
{
    private String dataBaseName = "jav";

    private String ip = "localhost";

    private int port = 3306;

    private String user = "root";

    private String pwd = "root";

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getUser()
    {
        return user;
    }

    public void setPwd(String pwd)
    {
        this.pwd = pwd;
    }

    public String getPwd()
    {
        return pwd;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getPort()
    {
        return port;
    }

    public void setDataBaseName(String dataBaseName)
    {
        this.dataBaseName = dataBaseName;
    }

    public String getDataBaseName()
    {
        return dataBaseName;
    }
}
