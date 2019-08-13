package com.org.shoppingcart.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.web.context.annotation.RequestScope;

import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.dto.ProductDto;
import com.org.shoppingcart.request.ProductRequest;
import com.org.shoppingcart.response.ProductResponse;
import com.org.shoppingcart.service.impl.ProductServiceImpl;

@RequestScope
@ManagedBean
public class UploadBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String message;
	private ProductDto product;
	private ProductRequest productRequest;
	private UploadedFile imageFile;

	private static final Logger logger = Logger.getLogger(UploadBean.class);

	public UploadBean() {
		this.product = new ProductDto();
		this.productRequest = new ProductRequest();
	}

	public String uploadFile() throws ApplicationException {
		logger.info("Bigin [UploadBean] method upload file");
		if (imageFile != null) {
			try {
				ProductResponse response = submitProductDetails(imageFile);
				if (response.getStatusCode() == 200) {
					return "index?faces-redirect=true";
				} else {
					setMessage("Error, Product details add failed");
				}
			} catch (Exception e) {
				logger.error("Error : " + e.getMessage());
				setMessage("Error, Error occured in Product details submitting to the database");
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("File not found"));
			setMessage("Error, File Not Found");
		}
		return null;
	}

	private ProductResponse submitProductDetails(UploadedFile imageFile) throws ApplicationException {
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		this.product.setStatus(true);
		this.productRequest.setProductDto(this.product);
		// submit product details
		return productServiceImpl.addNewProduct(imageFile, this.productRequest);
	}

	public void handleFileUpload(FileUploadEvent event) {
		FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UploadedFile getImageFile() {
		return imageFile;
	}

	public void setImageFile(UploadedFile imageFile) {
		this.imageFile = imageFile;
	}

}
