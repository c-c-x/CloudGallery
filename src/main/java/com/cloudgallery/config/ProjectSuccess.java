package com.cloudgallery.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

@Component
@Order(1)
@Slf4j
public class ProjectSuccess implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        String commandLog = ("\n")
                +
                """
                                                                                                                                        
                                                                                                                                        
                        VVVVVVVVVV       VV                                                                                             
                        VVVVVVVVVVV      VV                           VVVVV                 VVV      VVVVVV                    VVVVV    
                            VVV          VVV                   V      VVVVV   VVVVVVVVV     VVVV     VVVVVV  VVVVVVV     VV     VVVVVV  
                            VVV          VVV        VVVVVVVVV  VV     VVVVV VVVVVVVVVVVV    VVVVV    VVVVVV  VVVVVVVVV   VVV    VVVVVVV 
                            VVVV         VVV      VVVV    VVVV VVV    VVVVV VVVVV    VVV      VVVVV   VVVVVV VVVVVVVVVVV  VVV     VVVVV 
                            VVVV         VVVV     VVV      VVVV VVV  VVVVV VVVV    VVVV        VVVVV   VVVVV VVVV   VVVVV VVV    VVVVV  
                            VVVV          VVV  VV VVV      VVVV VVVVVVVVV  VVVVVVVVVVV           VVVVVVVVVVV  VVV    VVVV VVVV  VVVVV   
                            VVVVV         VVVVVVVV VVV      VVV  VVVVVVVV  VVVVVVV                 VVVVVVVVV  VVV  VVVVV  VVVVVVVVVV    
                            VVVVV         VVVVVVVV  VVVVVVVVVV    VVVVVV   VVVVVVVVVVVVVVV             VVVVV  VVVVVVVVV   VVVVVVVVV     
                         VVVVVVVVVVVV     VVVVVVV    VVVVVVVV      VVV       VVVVVVVVVVVVV              VVVV    VVVVV       VVVVVV      
                         VVVVVVVVVVV                                             VVVVVVV                VVVV                            
                                                      VVVV                                             VVVVV                            
                                                   VVVVVVVV                 VVVVVVVVV   VVVV       VVV VVVVV                            
                                         VVVVVVVVVVV     VVV               VVV     VVVVVVVVVVV    VVVVVVVVV                             
                                        VV      VV       VVV              VVV        VVV   VVVV   VVVVVVVV                              
                                        VV              VVV               VVV               VVV    VVVVV                                
                                        VVV             VVV                VVVV            VVV                                          
                                         VVVV          VVV                  VVVVVV        VV                                            
                                           VVVV       VVV                       VVVVVVV VVV                                             
                                            VVVVVVV VVVV                           VVVVVVV                       
                                              VVVVVVVVV                               VVV                                              
                                                  VVVV                                                                                  
                                                                                                                                                            
                        """
                +("\n")
                +"------------------------------------------------启动成功------------------------------------------------";
        log.info(commandLog);
    }

}

