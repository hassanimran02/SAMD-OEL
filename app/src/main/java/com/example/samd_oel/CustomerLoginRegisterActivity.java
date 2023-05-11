package com.example.samd_oel;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.os.Bundle;

public class CustomerLoginRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login_register);
    }

    RegisterCustomerButton.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view)
        {
            String email = CustomerEmail.getText().toString();
            String password = CustomerPassword.getText().toString();

            if(TextUtils.isEmpty(email))
            {
                Toast.makeText(CustomerLoginRegisterActivity.this, "Please write your Email...", Toast.LENGTH_SHORT).show();
            }

            if(TextUtils.isEmpty(password))
            {
                Toast.makeText(CustomerLoginRegisterActivity.this, "Please write your Password...", Toast.LENGTH_SHORT).show();
            }

            else
            {
                loadingBar.setTitle("Please wait :");
                loadingBar.setMessage("While system is performing processing on your data...");
                loadingBar.show();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            currentUserId = mAuth.getCurrentUser().getUid();
                            customersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(currentUserId);
                            customersDatabaseRef.setValue(true);

                            Intent intent = new Intent(CustomerLoginRegisterActivity.this, CustomersMapActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                        }
                        else
                        {
                            Toast.makeText(CustomerLoginRegisterActivity.this, "Please Try Again. Error Occurred, while registering... ", Toast.LENGTH_SHORT).show();

                            loadingBar.dismiss();
                        }
                    }
                });
            }
        }
}