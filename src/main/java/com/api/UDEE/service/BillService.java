package com.api.UDEE.service;

import com.api.UDEE.Convertor.BillToBillDto;
import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Bill;
import com.api.UDEE.domain.Usuario;
import com.api.UDEE.dto.BillDto;
import com.api.UDEE.exceptions.notFound.AddressNotExistsByUser;
import com.api.UDEE.exceptions.notFound.BillNotExistsException;
import com.api.UDEE.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;
    UsuarioService userService;
    AddressService addressService;

    @Autowired
    BillToBillDto billToBillDto;

    @Autowired
    public BillService(BillRepository billRepository,UsuarioService userService, AddressService addressService) {
        this.billRepository=billRepository;
        this.userService=userService;
        this.addressService=addressService;
    }

    public Bill getBillById(Integer id) throws BillNotExistsException {
        return billRepository.findById(id).orElseThrow(()-> new BillNotExistsException("No Bill was found by that id"));
    }

    public Bill newBill(Bill bill) {
        System.out.println(bill);
            return billRepository.save(bill);
    }

    public Page allBills(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    public List<BillDto> allBillsByDates(Date since, Date until, Integer id){
        List<Bill> billList = billRepository.allBillsByDates(id,since,until);
        List<BillDto>billDtoList= new ArrayList<>();
        for (int i=0;i<billList.size();i++){
            billDtoList.add(billToBillDto.convertToDto(billList.get(i)));
        }
        return billDtoList;
    }

    public List<Bill> allBillUnpaid(Integer id){
        return billRepository.findAll();
    }

    public Object allBillUnpaidByUserAndAddress(Integer idClient, Integer idAddress) throws AddressNotExistsByUser {
            Usuario user;
            user = userService.getUserById(idClient);
            Address address;
            address = addressService.getAddressById(idAddress);

            if((address.getUserClient().getId())==(user.getId())){
                List<Bill> billList = billRepository.allBillUnpaidByUserAndAddress(idClient,idAddress);
                List<BillDto>billDtoList= new ArrayList<>();
                    for (int i=0;i<billList.size();i++){
                        billDtoList.add(billToBillDto.convertToDto(billList.get(i)));
                    }
                    return billDtoList;
            }
            else{
                throw new AddressNotExistsByUser("La direccion no tiene asignado ese usuario");
            }
    }
}
