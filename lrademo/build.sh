# Oracle Transaction Manager for Microservices
 
# Copyright © 2022, Oracle and/or its affiliates. All rights reserved.

# This software and related documentation are provided under a license agreement containing restrictions on use and disclosure and are protected by intellectual property laws. Except as expressly permitted in your license agreement or allowed by law, you may not use, copy, reproduce, translate, broadcast, modify, license, transmit, distribute, exhibit, perform, publish, or display any part, in any form, or by any means. Reverse engineering, disassembly, or decompilation of this software, unless required by law for interoperability, is prohibited.

# The information contained herein is subject to change without notice and is not warranted to be error-free. If you find any errors, please report them to us in writing.

# If this is software or related documentation that is delivered to the U.S. Government or anyone licensing it on behalf of the U.S. Government, then the following notice is applicable:

# U.S. GOVERNMENT END USERS: Oracle programs, including any operating system, integrated software, any programs installed on the hardware, and/or documentation, delivered to U.S. Government end users are "commercial computer software" pursuant to the applicable Federal Acquisition Regulation and agency-specific supplemental regulations. As such, use, duplication, disclosure, modification, and adaptation of the programs, including any operating system, integrated software, any programs installed on the hardware, and/or documentation, shall be subject to license terms and license restrictions applicable to the programs. No other rights are granted to the U.S. Government.

# This software or hardware is developed for general use in a variety of information management applications. It is not developed or intended for use in any inherently dangerous applications, including applications that may create a risk of personal injury. If you use this software or hardware in dangerous applications, then you shall be responsible to take all appropriate fail-safe, backup, redundancy, and other measures to ensure its safe use. Oracle Corporation and its affiliates disclaim any liability for any damages caused by use of this software or hardware in dangerous applications.
# Oracle and Java are registered trademarks of Oracle and/or its affiliates. Other names may be trademarks of their respective owners.
# Intel and Intel Xeon are trademarks or registered trademarks of Intel Corporation. All SPARC trademarks are used under license and are trademarks or registered trademarks of SPARC International, Inc. AMD, Opteron, the AMD logo, and the AMD Opteron logo are trademarks or registered trademarks of Advanced Micro Devices. UNIX is a registered trademark of The Open Group.

# This software or hardware and documentation may provide access to or information about content, products, and services from third parties. Oracle Corporation and its affiliates are not responsible for and expressly disclaim all warranties of any kind with respect to third-party content, products, and services unless otherwise set forth in an applicable agreement between you and Oracle. Oracle Corporation and its affiliates will not be responsible for any loss, costs, or damages incurred due to your access to or use of third-party content, products, or services, except as set forth in an applicable agreement between you and Oracle.
#!/bin/bash

# Build hotel service

printf "${GREEN}\nEnter docker image registry prefix for image tagging. (This is a requirement to tag and push images to the registry). Example: iad.ocir.io/oabcs1 \n"
read CLUSTER_PREFIX


cd hotel
docker image build -t=hotel .
docker image tag hotel $CLUSTER_PREFIX/hotel
docker image push $CLUSTER_PREFIX/hotel
cd ../

# Build flight service
cd flight
docker image build -t=flight .
docker image tag flight $CLUSTER_PREFIX/flight
docker image push $CLUSTER_PREFIX/flight
cd ../

# Build trip manager service
cd trip-manager
docker image build -t=trip-manager .
docker image tag trip-manager $CLUSTER_PREFIX/trip-manager
docker image push $CLUSTER_PREFIX/trip-manager
cd ../

cd console
docker image build -t=console .
docker image tag console $CLUSTER_PREFIX/console
docker image push $CLUSTER_PREFIX/console

