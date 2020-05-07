package be.pxl.student.rest;

import be.pxl.student.entity.Account;
import be.pxl.student.rest.resources.AccountCreateResource;
import be.pxl.student.rest.resources.AccountResource;
import be.pxl.student.rest.resources.ErrorMessage;
import be.pxl.student.rest.resources.LabelResource;
import be.pxl.student.rest.resources.PaymentOverviewResource;
import be.pxl.student.rest.resources.PaymentsSearchResource;
import be.pxl.student.util.exception.AccountNotFoundException;
import be.pxl.student.entity.Payment;
import be.pxl.student.rest.resources.PaymentCreateResource;
import be.pxl.student.rest.resources.PaymentResource;
import be.pxl.student.service.AccountService;
import be.pxl.student.util.exception.DuplicateAccountException;

import javax.inject.Inject;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Path("/accounts")
public class AccountsRest {

	@Inject
	private AccountService accountService;

	@POST
	@Produces("application/json")
	public Response createAccount(AccountCreateResource accountCreateResource) {
		try {
			accountService.createOrUpdateAccount(accountCreateResource.getIban(), accountCreateResource.getName());
			return Response.created(UriBuilder.fromPath("/accounts/" + accountCreateResource.getName()).build()).build();
		} catch (DuplicateAccountException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(e)).build();
		}


	}

	@DELETE
	@Path("{accountId}")
	@Produces("application/json")
	public Response deleteAccount(@PathParam("accountId") long accountId) {
		accountService.removeAccount(accountId);
		return Response.status(Response.Status.ACCEPTED).build();
	}


	@GET
	@Path("{name}")
	@Produces("application/json")
	public Response getPayments(@PathParam("name") String name, @BeanParam PaymentsSearchResource searchCriteria) {
		try {
			List<Payment> payments = accountService.findPaymentsByAccountName(name);
			List<Payment> filteredPayments = payments.stream().filter(searchCriteria.getFilter()).collect(Collectors.toList());
			PaymentOverviewResource paymentOverview = new PaymentOverviewResource();
			paymentOverview.setPayments(mapPayments(filteredPayments));
			double receivingAmount = filteredPayments.stream().mapToDouble(Payment::getAmount).filter(a -> a > 0).sum();
			paymentOverview.setReceivingAmount(receivingAmount);
			double spendingAmount = filteredPayments.stream().mapToDouble(Payment::getAmount).filter(a -> a < 0).sum();
			paymentOverview.setSpendingAmount(spendingAmount);
			paymentOverview.setResultAmount(receivingAmount + spendingAmount);
			return Response.ok(paymentOverview).build();
		} catch (AccountNotFoundException e) {
			return Response.status(Response.Status.BAD_REQUEST).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("{name}")
	public Response addPayment(@PathParam("name") String name, PaymentCreateResource paymentCreateResource) {
		try {
			accountService.addPayment(name, paymentCreateResource.getCounterAccount(), paymentCreateResource.getAmount(), paymentCreateResource.getDetail(), paymentCreateResource.getDate());
			return Response.created(UriBuilder.fromPath("/accounts/" + name).build()).build();
		} catch (AccountNotFoundException e) {
			return Response.status(Response.Status.NOT_FOUND).type(MediaType.TEXT_PLAIN).entity(e.getMessage()).build();
		}
	}

	private List<PaymentResource> mapPayments(List<Payment> payments) {
		return payments.stream().map(p -> mapPayment(p)).sorted(Comparator.comparing(PaymentResource::getDate).reversed()).collect(Collectors.toList());
	}

	private List<AccountResource> mapAccounts(List<Account> accounts) {
		return accounts.stream().map(a -> mapAccount(a)).collect(Collectors.toList());
	}

	private AccountResource mapAccount(Account account) {
		AccountResource result = new AccountResource();
		result.setId(account.getId());
		result.setIban(account.getIBAN());
		result.setName(account.getName());
		return result;
	}

	private PaymentResource mapPayment(Payment payment) {
		PaymentResource result = new PaymentResource();
		if (payment.getLabel() != null) {
			LabelResource label = new LabelResource();
			label.setId(payment.getLabel().getId());
			label.setName(payment.getLabel().getName());
			result.setLabel(label);
		}
		result.setId(payment.getId());
		result.setAmount(payment.getAmount());
		result.setCounterAccount(payment.getCounterAccount().getIBAN());
		result.setCurrency(payment.getCurrency());
		result.setDetail(payment.getDetail());
		result.setDate(payment.getDate().toLocalDate());
		return result;
	}

}
