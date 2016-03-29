/*
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
*/
/**
 * Created by emkasun on 21/7/2015.
 *//*

public class SFTPTransfer {
    public static void main(String args[]){
        SFTPTransfer.transfer(args[0],args[1],args[2],args[3],args[4]);
        //SFTPTransfer.transfer("/root/","D:\\tmp\\test.txt","root","rootroot","172.30.51.36");
    }
    public static void transfer(String RemoteDirectoryPath, String localfilepath,String username, String password, String hostname) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, hostname, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println("connected to "+hostname+" as "+username+" password as "+password);
            ChannelSftp channel = null;
            channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            File localFile = new File(localfilepath);
            //If you want you can change the directory using the following line.
            channel.cd(RemoteDirectoryPath);
            System.out.println("changed directory");
            channel.put(new FileInputStream(localFile), localFile.getName());
            System.out.println("file transfer success");
            channel.disconnect();
            session.disconnect();
        } catch (com.jcraft.jsch.JSchException jsce) {
            System.out.println(jsce.toString());
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
        } catch (com.jcraft.jsch.SftpException sftpe) {
            System.out.println(sftpe.toString());
        }
    }
}
*/
