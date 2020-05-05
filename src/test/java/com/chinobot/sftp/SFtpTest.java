package com.chinobot.sftp;

import java.io.Console;

public class SFtpTest {
    public static void main(String[] args) {
        String ip = "129.204.108.69";
        String user = "root";
        String pwd = "CHinobot@#2019";
        String port = "22";

        String sourcePath = "C:\\Users\\Public\\Pictures\\Sample Pictures";
        String destinationPath = "/data/temp/";

        Login.sshSftp(ip, user, pwd, Integer.parseInt(port), sourcePath,
                destinationPath);

    }
}
