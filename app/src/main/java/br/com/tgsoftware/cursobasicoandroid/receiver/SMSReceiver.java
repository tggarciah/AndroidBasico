package br.com.tgsoftware.cursobasicoandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.widget.Toast;

import br.com.tgsoftware.cursobasicoandroid.dao.AlunoDao;

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String format = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(pdu, format);
        String telefone = sms.getDisplayOriginatingAddress();

        AlunoDao dao = new AlunoDao(context);

        if (dao.ehAluno(telefone)) {
            Toast.makeText(context, "Chegou um SMS de Aluno", Toast.LENGTH_SHORT).show();
        }
        dao.close();
    }
}