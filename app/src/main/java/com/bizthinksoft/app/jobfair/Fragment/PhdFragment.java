package com.bizthinksoft.app.jobfair.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bizthinksoft.app.jobfair.Activity.Dashboard;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;
import com.bizthinksoft.app.jobfair.Utility.FilePath;
import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.API;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhdFragment extends Fragment {


    EditText speciliasation,insitiute,pssing_out_year,totalmarks,outoffmarks,pernentage,grades;
    Spinner course,course_type;
    private static final int PICK_PDF_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    Uri filePath;
    AppSharedPreferences sharedPreferences;
    TextView uploaddoc;
    ProgressDialog progressBar;
    Button submitform;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_phd, container, false);

        ActionBar actionBar =((Dashboard) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Doctorate & PHD Detail");
        sharedPreferences  =  new AppSharedPreferences(getActivity());

        setHasOptionsMenu(true);
        uploaddoc = (TextView)view.findViewById(R.id.uploaddoc);

        speciliasation = view.findViewById(R.id.speciliasation);
        insitiute = view.findViewById(R.id.insitiute);
        pssing_out_year = view.findViewById(R.id.pssing_out_year);
        totalmarks = view.findViewById(R.id.totalmarks);
        outoffmarks = view.findViewById(R.id.outoffmarks);
        pernentage = view.findViewById(R.id.pernentage);
        grades = view.findViewById(R.id.grades);
        course = view.findViewById(R.id.course);
        course_type = view.findViewById(R.id.course_type);

        progressBar =  new ProgressDialog(getActivity());
        uploaddoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestStoragePermission();
                showFileChooser();
            }
        });
        submitform = view.findViewById(R.id.submitform);
        submitform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(speciliasation.getText().toString().isEmpty())
                {

                    Toasty.error(getActivity(), "Please select specialization", Toast.LENGTH_SHORT, true).show();

                }
                else  if(insitiute.getText().toString().isEmpty())
                {
                    Toasty.error(getActivity(), "Please enter university institute", Toast.LENGTH_SHORT, true).show();
                }
                else  if(pssing_out_year.getText().toString().isEmpty())
                {
                    Toasty.error(getActivity(), "Please enter passing out year", Toast.LENGTH_SHORT, true).show();
                }
                else  if(totalmarks.getText().toString().isEmpty())
                {
                    Toasty.error(getActivity(), "Please enter total marks obtained", Toast.LENGTH_SHORT, true).show();
                }
                else  if(outoffmarks.getText().toString().isEmpty())
                {
                    Toasty.error(getActivity(), "Please enter total marks out of", Toast.LENGTH_SHORT, true).show();
                }


                else {

                    uploadMultipart();
                }
            }
        });

        return  view;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST);
    }

    //handling the ima chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(getActivity(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(getActivity(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void uploadMultipart() {
        //getting name for the pdf

        String path = FilePath.getPath(getActivity(), filePath);


        if (path == null) {

            Toast.makeText(getActivity(), "Please move your .pdf file to internal storage and retry", Toast.LENGTH_LONG).show();
        } else {

            //Uploading code
            try {

                String uploadId = UUID.randomUUID().toString();


                //Creating a multi part request
                new MultipartUploadRequest(getActivity(), uploadId, API.AddPHD)
                        .addFileToUpload(path, "phd_upload_document") //Adding file
                        .addParameter("phd_course_type", speciliasation.getText().toString())
                        .addParameter("phd_course_name", speciliasation.getText().toString())
                        .addParameter("phd_course_type", speciliasation.getText().toString())
                        .addParameter("phd_university_institute", insitiute.getText().toString())
                        .addParameter("phd_passing_out_year", pssing_out_year.getText().toString())
                        .addParameter("phd_total_marks_obtained", totalmarks.getText().toString())
                        .addParameter("phd_total_marks_out_of", outoffmarks.getText().toString())
                        .addParameter("phd_grading_system", outoffmarks.getText().toString())
                        .addParameter("c_id", sharedPreferences.pref.getString("mast_id",""))//Adding text parameter to the request
                        .setNotificationConfig(new UploadNotificationConfig())
                        .setMaxRetries(2)
                        .setDelegate(new UploadStatusDelegate() {
                            @Override
                            public void onProgress(Context context, UploadInfo uploadInfo) {

                            }

                            @Override
                            public void onError(Context context, UploadInfo uploadInfo, Exception exception) {

                            }

                            @Override
                            public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                                Log.d("serverResponse", String.valueOf(serverResponse.getBody()));
                                if(serverResponse.getHttpCode()==200)
                                {
                                    Toast.makeText(context, ""+serverResponse.getHttpCode(), Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancelled(Context context, UploadInfo uploadInfo) {

                            }
                        })
                        .startUpload(); //Starting the upload

            } catch (Exception exc) {
                Toast.makeText(getActivity(), exc.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == android.R.id.home) {
            if (getActivity() != null) {
                getActivity().onBackPressed();
            }
            return true;
        };
        return super.onOptionsItemSelected(item);
    }


}
