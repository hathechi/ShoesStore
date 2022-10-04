package com.example.shoesstore.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.Adapter.QLSanPhamAdapter;
import com.example.shoesstore.DAO.SanPhamDAO;
import com.example.shoesstore.Moder.SanPhamMain;
import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSanPham} factory method to
 * create an instance of this fragment.
 */
public class FragmentSanPham extends Fragment {
    private final List<SanPhamMain> sanPhamMains = new ArrayList<>();
    private final List<String> listSpiner = new ArrayList<>();
    ImageButton imgButton_thuonghieu;
    BottomSheetDialog dialog;
    Spinner spinner;
    ImageView iv_view;
    Uri uriImage;
    String name_thuonghieu;
    RecyclerView rcvQlsanpham;
    private QLSanPhamAdapter qlSanPhamAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);

        //set Title cho toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản Lý Sản Phẩm");

        //set Adapter cho fragment quản lý sản phẩm
        rcvQlsanpham = view.findViewById(R.id.rcv_sanpham_fragment);
        setAdapterQLsanpham();


        imgButton_thuonghieu = view.findViewById(R.id.imgButton_add_thuonghieu);
        imgButton_thuonghieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogsheetview = LayoutInflater.from(getContext()).inflate(R.layout.add_listview, null);
                dialog = new BottomSheetDialog(getContext());
                dialog.setContentView(dialogsheetview);
                dialog.show();

                Button btnAdd = dialogsheetview.findViewById(R.id.btnAdd);
                EditText etIDsp = dialogsheetview.findViewById(R.id.etIDsp);
                EditText etTensp = dialogsheetview.findViewById(R.id.etTensp);
                EditText etGiasp = dialogsheetview.findViewById(R.id.etGia);
                EditText etMota = dialogsheetview.findViewById(R.id.etMota);
                EditText etChitiet = dialogsheetview.findViewById(R.id.etChitiet);
                //spinner
                spinner = dialogsheetview.findViewById(R.id.spinnerAdd);
                setAdapterSpinner();
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        name_thuonghieu = spinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                iv_view = dialogsheetview.findViewById(R.id.iv_view);

                iv_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, 1);
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String idSp = etIDsp.getText().toString().trim();
                        String tensp = etTensp.getText().toString();
                        String giasp = (etGiasp.getText().toString());
                        String mota = etMota.getText().toString();
                        String chitiet = etChitiet.getText().toString();


                        if (tensp.isEmpty() || giasp.isEmpty() || idSp.isEmpty() || mota.isEmpty() || name_thuonghieu == null) {
                            FancyToast.makeText(getContext(), "Chưa Nhập Đủ Dữ Liệu !", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                        } else {
                            if (uriImage == null) {
                                FancyToast.makeText(getContext(), "Chưa Chọn Hình !", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                return;
                            }

                            //check xem id nhập vào có trùng với id cũ hay không
                            for (SanPhamMain check : sanPhamMains) {
                                if (idSp.equals(String.valueOf(check.getId_sanpham1()))) {
                                    FancyToast.makeText(getContext(), "ID Trùng Nhau! - Nhập ID Mới ", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                                    return;

                                }
                            }
                            //up thông tin sản phẩm lên firebase
                            uploadSanPhamtoFirebase(uriImage, tensp, mota, chitiet, Integer.parseInt(giasp), Integer.parseInt(idSp), name_thuonghieu);

                        }

                    }
                });
            }
        });

        return view;
    }


    private void setAdapterSpinner() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("thuonghieu");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (listSpiner != null) {
                    listSpiner.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ThuongHieu thuongHieu = dataSnapshot.getValue(ThuongHieu.class);
                    listSpiner.add(thuongHieu.getName_thuonghieu());
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listSpiner);
        spinner.setAdapter(arrayAdapter);

    }

    private void setAdapterQLsanpham() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                SanPhamMain sanPham = snapshot.getValue(SanPhamMain.class);
                sanPhamMains.add(sanPham);
                qlSanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                SanPhamMain sanPham = snapshot.getValue(SanPhamMain.class);
                if (sanPham == null || sanPhamMains.isEmpty() || sanPhamMains == null) {
                    return;
                }
                for (int i = 0; i < sanPhamMains.size(); i++) {
                    if (sanPham.getId_sanpham1() == sanPhamMains.get(i).getId_sanpham1()) {
                        sanPhamMains.set(i, sanPham);
                        break;
                    }
                }
                qlSanPhamAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                SanPhamMain sanPham = snapshot.getValue(SanPhamMain.class);
                if (sanPham == null || sanPhamMains.isEmpty() || sanPhamMains == null) {
                    return;
                }
                for (int i = 0; i < sanPhamMains.size(); i++) {
                    if (sanPham.getId_sanpham1() == sanPhamMains.get(i).getId_sanpham1()) {
                        sanPhamMains.remove(sanPhamMains.get(i));
                        break;
                    }
                }
                qlSanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        LinearLayoutManager linearLayout = new LinearLayoutManager(getContext());
        rcvQlsanpham.setLayoutManager(linearLayout);
        qlSanPhamAdapter = new QLSanPhamAdapter(getContext(), sanPhamMains, new QLSanPhamAdapter.IclickListener() {
            @Override
            public void onClickupdateItem(SanPhamMain sanPhamMain) {
                EditSanPham(sanPhamMain);
            }

            @Override
            public void onClickdeleteItem(SanPhamMain sanPhamMain) {
                DeleteSanPham(sanPhamMain);
            }
        });
        rcvQlsanpham.setAdapter(qlSanPhamAdapter);

    }

    private void DeleteSanPham(SanPhamMain sanPhamMain) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa Item")
                .setMessage("Bạn có chắc muốn xóa sản phẩm này không?")
                .setPositiveButton("Có ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SanPhamDAO sanPhamDAO = new SanPhamDAO();
                        sanPhamDAO.DeletetSanPham(sanPhamMain);
                        FancyToast.makeText(getContext(), "Xóa Thành Công !", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    }
                })
                .setNegativeButton("Không ", null)
                .show();

    }

    private void EditSanPham(SanPhamMain sanPhamMain) {


        View dialogsheetview = LayoutInflater.from(getContext()).inflate(R.layout.edit_listview, null);
        dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(dialogsheetview);
        dialog.show();

        EditText ten_edit = dialogsheetview.findViewById(R.id.etTensp_edit);
        EditText gia_edit = dialogsheetview.findViewById(R.id.etGiasp_edit);
        EditText mota_edit = dialogsheetview.findViewById(R.id.etMota_edit);
        EditText chitiet_edit = dialogsheetview.findViewById(R.id.etChitiet_edit);

        spinner = dialogsheetview.findViewById(R.id.spinnerAdd);
        setAdapterSpinner();
        iv_view = dialogsheetview.findViewById(R.id.iv_view);

        iv_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        Button edit = dialogsheetview.findViewById(R.id.btnEdit);
        ten_edit.setText(sanPhamMain.getName());
        gia_edit.setText(sanPhamMain.getGia() + "");
        mota_edit.setText(sanPhamMain.getMota());
        chitiet_edit.setText(sanPhamMain.getChitiet());
        spinner.setSelection(listSpiner.indexOf(sanPhamMain.getThuonghieu()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                name_thuonghieu = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Glide.with(getContext()).load(sanPhamMain.getURLImage()).into(iv_view);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = ten_edit.getText().toString().trim();
                String gia = (gia_edit.getText().toString().trim());
                String mota = mota_edit.getText().toString().trim();
                String chitiet = chitiet_edit.getText().toString().trim();


                if (ten.isEmpty() || gia.isEmpty() || mota.isEmpty() || chitiet.isEmpty() || name_thuonghieu == null || uriImage == null) {
                    FancyToast.makeText(getContext(), "NHẬP ĐỦ DỮ LIỆU", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                } else {

                    updateImage(sanPhamMain, ten, gia, mota, chitiet, name_thuonghieu);

                }
            }
        });

    }

    private void updateImage(SanPhamMain sanPhamMain, String ten, String gia, String mota, String chitiet, String thuonghieu) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://realtimedata-1e0aa.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child(System.currentTimeMillis() + ".PNG");
        //Tạo dialog delay
        ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Chờ Chút", "Sửa Thông Tin Xong ngay đây !!", true);

//        //check hình khi chỉnh sửa
//        if (uriImage == null) {
//            uriImage = Uri.parse(sanPhamMain.getURLImage());
//            Log.i("a", "updateImage: " + uriImage);
//        }
        mountainImagesRef.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        sanPhamMain.setGia(Integer.parseInt(gia));
                        sanPhamMain.setMota(mota);
                        sanPhamMain.setChitiet(chitiet);
                        sanPhamMain.setName(ten);
                        sanPhamMain.setThuonghieu(thuonghieu);
                        sanPhamMain.setURLImage(uri.toString());
                        SanPhamDAO sanPhamDAO = new SanPhamDAO();
                        sanPhamDAO.updateSanPham(sanPhamMain);

                        progressDialog.dismiss();
                        dialog.cancel();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                FancyToast.makeText(getContext(), "Lỗi Kết Nối !!!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                progressDialog.dismiss();
            }
        });

    }

    private void uploadSanPhamtoFirebase(Uri uriImage, String name, String mota, String chitiet, int gia, int idsp, String thuonghieu) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://realtimedata-1e0aa.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference mountainImagesRef = storageRef.child(System.currentTimeMillis() + ".PNG");

        //Tạo dialog delay
        ProgressDialog progressDialog = ProgressDialog.show(getContext(), "Chờ Chút", "Thêm Sản Phẩm Xong ngay đây !!", true);

        mountainImagesRef.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                mountainImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        SanPhamDAO sanPhamDAO = new SanPhamDAO();
                        sanPhamDAO.insertSanPham(new SanPhamMain(idsp, gia, name, thuonghieu, mota, chitiet, uri.toString()));

                        progressDialog.dismiss();
                        dialog.cancel();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //lấy dữ liệu hình từ máy
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == -1 && data != null) {
            uriImage = data.getData();
            Log.i("uri", "uri: " + uriImage);
            if (uriImage != null) {
                iv_view.setImageURI(uriImage);
            }
        }
    }
}