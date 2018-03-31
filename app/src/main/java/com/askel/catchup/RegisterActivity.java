package com.askel.catchup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText name, email, password;
    private FirebaseAuth mAuthM;
    private DatabaseReference mDatabaseM;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name=findViewById(R.id.editUsername);
        email=findViewById(R.id.editemail);
        password=findViewById(R.id.editPassword);
        mAuthM=FirebaseAuth.getInstance();
        mDatabaseM= FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void registerButtonClicked(View view) {
        final String name_content, password_content, email_content;
        name_content = name.getText().toString().trim();
        password_content = password.getText().toString().trim();
        email_content = email.getText().toString().trim();

        if (!TextUtils.isEmpty(email_content) && !TextUtils.isEmpty(name_content) && !TextUtils.isEmpty(password_content)) {
            mAuthM.createUserWithEmailAndPassword(email_content, password_content).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String user_id=mAuthM.getCurrentUser().getUid();
                        DatabaseReference current_user_db=mDatabaseM.child(user_id);
                        current_user_db.child("Name").setValue(name_content);
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }
            });
        }

    }
}
