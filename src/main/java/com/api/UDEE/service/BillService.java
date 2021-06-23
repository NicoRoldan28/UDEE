package com.api.UDEE.service;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Bill;
import com.api.UDEE.domain.User;
import com.api.UDEE.exceptions.notFound.AddressNotExistsException;
import com.api.UDEE.exceptions.notFound.BillNotExistsException;
import com.api.UDEE.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillService {
    private final BillRepository billRepository;
    UserService userService;
    AddressService addressService;

    @Autowired
    public BillService(BillRepository billRepository,UserService userService, AddressService addressService) {
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

    public List<Bill> allBillsByDates(Date from, Date to, Integer id){
        return billRepository.findAll();
    }

    public List<Bill> allBillUnpaid(Integer id){
        return billRepository.findAll();
    }

    public List<Bill> allBillUnpaidByUserAndAddress(Integer idClient, Integer idAddress) throws AddressNotExistsException {
        User user= new User();
        user = userService.getUserById(idClient);
        Address address= new Address();
        address = addressService.getAddressById(idAddress);
        return billRepository.findAll();
    }
}
