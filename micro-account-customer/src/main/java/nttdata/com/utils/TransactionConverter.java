package nttdata.com.utils;

import nttdata.com.dto.AccountDTO;
import nttdata.com.dto.CreditCardDTO;
import nttdata.com.dto.TransactionDTO;
import nttdata.com.model.Account;
import nttdata.com.model.CreditCard;
import nttdata.com.model.Transaction;

public class TransactionConverter {

    public static TransactionDTO transactionToTransactionDTO(Transaction entity) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(entity.getId());
        dto.setType(entity.getType());
        dto.setAmount(entity.getAmount());
        dto.setIdCreditCard(entity.getCreditCardId().getId());
        dto.setIdAccount(entity.getAccountId());
        dto.setTimestamp(entity.getTimestamp());

        return dto;
    }
    public static Transaction transactionDTOToTransaction(TransactionDTO dto, CreditCardDTO creditCardDTO, AccountDTO accountDTO) {
        Transaction entity = new Transaction();
        entity.setId(dto.getTransactionId());
        entity.setType(dto.getType());
        entity.setAmount(dto.getAmount());
        entity.setTimestamp(dto.getTimestamp());
        CreditCard creditCardEntity = CreditCardConverter.DTOToCreditCard(creditCardDTO);
        entity.setCreditCardId(creditCardEntity);
        Account accountEntity = AccountConverter.accountDTOToAccount(accountDTO);
        entity.setAccountId(String.valueOf(accountEntity));

        return entity;
    }
}