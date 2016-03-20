/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.doubotis.mvc.core;

import be.doubotis.http.exceptions.HTTPException;

/**
 *
 * @author Christophe
 */
public interface ServletControllerContext {
    
    public HTTPException getException();
    
}
