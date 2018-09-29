package com.bizthinksoft.app.jobfair.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bizthinksoft.app.jobfair.Activity.Dashboard;
import com.bizthinksoft.app.jobfair.R;
import com.bizthinksoft.app.jobfair.Utility.API;
import com.bizthinksoft.app.jobfair.Utility.AppSharedPreferences;
import com.bizthinksoft.app.jobfair.Utility.FilePath;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HscFragment extends Fragment {


    EditText speciliasation,insitiute,pssing_out_year,totalmarks,outoffmarks,pernentage,grades;
    Spinner course;
    private static final int PICK_PDF_REQUEST = 1;
    private static final int STORAGE_PERMISSION_CODE = 123;
    Uri filePath;
    AppSharedPreferences sharedPreferences;
    TextView uploaddoc;
    Future<File> uploading;
    TextView uploadCount;
    ProgressBar progressbar;
    ProgressDialog progressBar;
    Button submitform;
    boolean uploadFlag = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_hsc, container, false);
        ActionBar actionBar =((Dashboard) getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("HSC Detail");
        sharedPreferences =  new AppSharedPreferences(getContext());
        Ion.getDefault(getActivity()).configure().setLogging("ion-sample", Log.DEBUG);
        setHasOptionsMenu(true);
        uploaddoc = (TextView)view.findViewById(R.id.uploaddoc);
        uploadCount = (TextView)view.findViewById(R.id.upload_count);
        progressbar = (ProgressBar)view.findViewById(R.id.progress);

        progressbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#C0D000"), PorterDuff.Mode.MULTIPLY);
        speciliasation = view.findViewById(R.id.speciliasation);
        insitiute = view.findViewById(R.id.insitiute);
        pssing_out_year = view.findViewById(R.id.pssing_out_year);
        totalmarks = view.findViewById(R.id.totalmarks);
        outoffmarks = view.findViewById(R.id.outoffmarks);
        pernentage = view.findViewById(R.id.pernentage);
        grades = view.findViewById(R.id.grades);
        course = view.findViewById(R.id.course);

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

                //uploadMultipart();
                 if(insitiute.getText().toString().isEmpty())
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
        GetPhpDetial();
        return  view;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,""), PICK_PDF_REQUEST);
    }

    //handling the ima chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            uploadFlag = true;
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


    public  void  GetPhpDetial()
    {
        progressBar.setTitle("Loading..");

        progressBar.show();

        Ion.with(getActivity())
                .load(API.CANDIDATEDETAIL)
                .setMultipartParameter("c_id", sharedPreferences.pref.getString("mast_id", ""))
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject obj = new JSONObject(result);
                            JSONObject Educational_detail = obj.getJSONObject("data");
                            selectValue(course,Educational_detail.getJSONObject("twelve").getString("ed_board_name"));
                            insitiute.setText(Educational_detail.getJSONObject("twelve").getString("ed_school_medium"));
                            pssing_out_year.setText(Educational_detail.getJSONObject("twelve").getString("ed_passout_yr"));
                            totalmarks.setText(Educational_detail.getJSONObject("twelve").getString("ed_total_marks_obtained"));
                            outoffmarks.setText(Educational_detail.getJSONObject("twelve").getString("ed_total_marks_out_of"));
                            pernentage.setText(Educational_detail.getJSONObject("twelve").getString("ed_percentage"));
                            grades.setText(Educational_detail.getJSONObject("twelve").getString("ed_grade"));
                            progressBar.hide();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
    }

    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    public void uploadMultipart(){




        if (uploadFlag == false) {

            if (uploading != null && !uploading.isCancelled()) {
                resetUpload();
                return;
            }


            Ion.with(getActivity())
                    .load(API.AddHsc)
                    .uploadProgressBar(progressbar)
                    .uploadProgressHandler(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {
                            //uploadCount.setText("" + getFileSize(downloaded) + " / " +getFileSize(total) );
                        }
                    })
                    .setMultipartParameter("twelve_board_name", course.getSelectedItem().toString())
                    .setMultipartParameter("twelve_school_medium", insitiute.getText().toString())
                    .setMultipartParameter("twelve_passing_out_year", pssing_out_year.getText().toString())
                    .setMultipartParameter("twelve_total_marks_obtained", totalmarks.getText().toString())
                    .setMultipartParameter("twelve_total_marks_out_of", outoffmarks.getText().toString())
                    .setMultipartParameter("twelve_grading_system", grades.getText().toString())
                    .setMultipartParameter("c_id", sharedPreferences.pref.getString("mast_id", ""))
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            //Log.d("response",result);
                            Toast.makeText(getActivity(), "Data Updated successfully", Toast.LENGTH_SHORT).show();

                            GetPhpDetial();

                        }



                    });

        } else {
            String path = FilePath.getPath(getActivity(), filePath);
            String content_type = getMimeType(new File(path));
            if (uploading != null && !uploading.isCancelled()) {
                resetUpload();
                return;
            }
            Ion.with(getActivity())
                    .load(API.AddHsc)
                    .uploadProgressBar(progressbar)
                    .uploadProgressHandler(new ProgressCallback() {
                        @Override
                        public void onProgress(long downloaded, long total) {
                            uploadCount.setText("" + getFileSize(downloaded) + " / " +getFileSize(total) );
                        }
                    })
                    .setMultipartParameter("twelve_board_name", course.getSelectedItem().toString())
                    .setMultipartParameter("twelve_school_medium", insitiute.getText().toString())
                    .setMultipartParameter("twelve_passing_out_year", pssing_out_year.getText().toString())
                    .setMultipartParameter("twelve_total_marks_obtained", totalmarks.getText().toString())
                    .setMultipartParameter("twelve_total_marks_out_of", outoffmarks.getText().toString())
                    .setMultipartParameter("twelve_grading_system", grades.getText().toString())
                    .setMultipartParameter("c_id", sharedPreferences.pref.getString("mast_id", ""))
                    .setMultipartFile("twelve_upload_document", content_type,new File(path))
                    .asString()
                    .setCallback(new FutureCallback<String>() {
                        @Override
                        public void onCompleted(Exception e, String result) {
                            Toast.makeText(getActivity(), "Data Updated successfully", Toast.LENGTH_SHORT).show();
                            GetPhpDetial();


                        }



                    });

        }

    }

    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
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
    public String getMimeType(File file) {
        String type = null;
        final String url = file.toString();
        final String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase());
        }
        if (type == null) {
            type = "image/*"; // fallback type. You might set it to */*
        }
        return type;
    }
    void resetUpload() {
        // cancel any pending upload
        uploading.cancel();
        uploading = null;
        // reset the ui
        uploadCount.setText(null);
        progressBar.setProgress(0);
    }



}
