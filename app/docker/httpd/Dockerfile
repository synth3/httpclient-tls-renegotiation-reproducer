# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

FROM httpd:2.4

ENV httpd_home /usr/local/apache2
ENV www_dir /var/httpd/www
ENV www_dir_withclientcert ${www_dir}/withclientcert

RUN apt-get update

RUN mkdir -p ${www_dir_withclientcert}

COPY test1.html ${www_dir}/
COPY test2.html ${www_dir_withclientcert}/

RUN mkdir ${httpd_home}/ssl
COPY server-cert.pem ${httpd_home}/ssl/
COPY server-key.pem ${httpd_home}/ssl/
COPY httpd.conf ${httpd_home}/conf/

EXPOSE 8443
