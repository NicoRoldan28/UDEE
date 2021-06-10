package com.api.UDEE.service;

import com.api.UDEE.domain.Address;
import com.api.UDEE.domain.Bill;
import com.api.UDEE.exceptions.AddressNotExistsException;
import com.api.UDEE.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    private final BillRepository billRepository;
    @Autowired
    public BillService(BillRepository billRepository){
        this.billRepository=billRepository;
    }

    public Bill getBillById(Integer id) throws AddressNotExistsException {
        return billRepository.findById(id).orElseThrow(AddressNotExistsException::new);
    }

    public Bill newBill(Bill bill) {
        if (!billRepository.existsById(bill.getId())) {
            return billRepository.save(bill);
        }
        else{
            return null;
        }
    }

    public Page allBills(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

}
