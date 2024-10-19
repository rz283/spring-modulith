@org.springframework.modulith.ApplicationModule(
        allowedDependencies = {
                "inventory::exposed",
                "eventaction::action",
                "exception"
        }
)
package com.example.springmodulith.order;